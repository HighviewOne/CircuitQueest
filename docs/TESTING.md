# Testing Guide

## Overview

CircuitQuEEst has 212 JVM unit tests and 53 instrumented tests (plus 6 store-screenshot tests that run separately). This guide explains the testing strategy, how to run tests, and how to add new ones.

## Test Pyramid

```
        ╔═══════════════════════════════════╗
        ║   UI Tests (24 tests)             ║
        ║   Compose Screen Components       ║
        ╠═══════════════════════════════════╣
        ║   Database Tests (29 tests)       ║
        ║   Room DAOs, Migrations, Import   ║
        ╠═══════════════════════════════════╣
        ║   Unit Tests (212 tests)          ║
        ║   Logic, Repository, Edge         ║
        ║   Cases, Content Modules          ║
        ╚═══════════════════════════════════╝
```

## Test Structure

### Unit Tests (`app/src/test/`)

**Pure Kotlin logic testing with mocked dependencies**

```kotlin
@RunWith(JUnit4::class)
class QuizScoringTest {
    @Test
    fun scoring_correctAnswer_earnsFullPoints() {
        // Arrange
        val correctAnswers = 8
        val totalQuestions = 10
        
        // Act
        val score = (correctAnswers * 100) / totalQuestions
        
        // Assert
        assertEquals(80, score)
    }
}
```

**Categories:**
1. **Business Logic** (14 tests) - QuizScoring, XP calculation
2. **Repository** (16 tests) - CRUD operations, data consistency
3. **ViewModels** (14 tests) - State management, lifecycle
4. **Edge Cases** (23 tests) - Boundary conditions, error handling
5. **Content** (84 tests) - All 42 topics validated
6. **DAO** (17 tests) - Mocked database operations
7. **Navigation** (9 tests) - Route definitions, arguments
8. **Other** (37 tests) - Utilities, services

### Instrumented Tests (`app/src/androidTest/`)

**Real Android environment with actual database & UI**

```kotlin
@RunWith(AndroidJUnit4::class)
class ProgressDaoInstrumentedTest {
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }
    
    @Test
    fun progressDao_insertAndRetrieve_success() = runBlocking {
        // Arrange
        val progress = Progress(topicId = "topic1", bestScore = 85, totalQuestions = 10)
        
        // Act
        progressDao.insertOrUpdate(progress)
        val retrieved = progressDao.getProgress("topic1")
        
        // Assert
        assertEquals(85, retrieved?.bestScore)
    }
}
```

**Categories:**
1. **Database** (35 tests) - Room operations, integrity
2. **Screens** (40 tests) - UI rendering, interactions
3. **Components** (20 tests) - Composable rendering
4. **Workflows** (8 tests) - Complex integration flows

## Running Tests

### All Tests
```bash
./gradlew test                      # Unit tests only
./gradlew connectedAndroidTest      # Instrumented tests (requires device/emulator; uninstalls the app afterwards — use an emulator, not a phone with real progress)
./gradlew test connectedAndroidTest # Both
# CI runs connectedDebugAndroidTest on an API 34 emulator (build.yml, `instrumented` job),
# excluding the store-screenshot generator (package ...screenshots, run by screenshots.yml).
# MigrationTest validates every Room schema step against app/schemas/ — add a case per new version.
```

### Specific Tests
```bash
# Run single test class
./gradlew test --tests QuizScoringTest

# Run single test method
./gradlew test --tests QuizScoringTest.scoring_perfectScore

# Run tests matching pattern
./gradlew test --tests "*Edge*"
```

### With Coverage Report
```bash
./gradlew test --coverage
# Report: build/reports/coverage/test/index.html
```

### Watch Mode (Auto-run on changes)
```bash
./gradlew test -t
```

## Test Counts

Coverage isn't measured (no JaCoCo/Kover set up), so this lists test counts only.

| Area | Unit (JVM) | Instrumented |
|------|-----------:|-------------:|
| Content modules + TopicsService | 84 | - |
| Scoring + edge cases | 35 | - |
| ViewModels | 25 | - |
| Room DAOs, migrations, legacy import | 22 | 29 |
| ProgressRepository | 18 | - |
| Navigation | 13 | - |
| Quiz flow (integration) | 6 | - |
| UI helpers (search, numeric parsing, fonts) | 9 | - |
| Compose components | - | 9 |
| Screens (Home, Quiz, Result) | - | 15 |
| **Total** | **212** | **53** |

`StoreScreenshots` (6 tests) only runs in `screenshots.yml`; `build.yml` excludes it.

## Adding New Tests

### Unit Test Template
```kotlin
package com.circuitqueest.app.util

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MyFeatureTest {
    @Test
    fun myFeature_expectedBehavior_producesResult() {
        // Arrange - Set up test data
        val input = "test"
        
        // Act - Execute the function
        val result = myFunction(input)
        
        // Assert - Verify the result
        assertEquals("expected", result)
    }
}
```

### Instrumented Test Template
```kotlin
package com.circuitqueest.app.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myScreen_displaysCorrectly() {
        // Act
        composeTestRule.setContent {
            MyScreen()
        }

        // Assert
        composeTestRule.onNodeWithText("Expected Text").assertIsDisplayed()
    }
}
```

### Test Naming Convention
```
functionName_inputCondition_expectedResult()

Examples:
- scoring_perfectScore_returnsHundred()
- progress_multipleAttempts_tracksbestScore()
- homeScreen_withTopics_displaysAll()
```

### Testing Best Practices

1. **AAA Pattern** - Arrange, Act, Assert
2. **Single Responsibility** - One assertion per test (when possible)
3. **Descriptive Names** - Test name explains what's being tested
4. **No Side Effects** - Tests should not depend on execution order
5. **Mock External Dependencies** - Isolate the unit under test
6. **Use Test Fixtures** - Reusable test data

## Test Examples

### Example 1: Unit Test (Business Logic)
```kotlin
@Test
fun scoring_eightOutOfTen_returns80Percent() {
    val correctAnswers = 8
    val totalQuestions = 10
    
    val score = (correctAnswers * 100) / totalQuestions
    
    assertEquals(80, score)
}
```

### Example 2: Mocked Unit Test
```kotlin
@Test
fun repository_getProgress_callsDao() {
    // Arrange
    val mockDao: ProgressDao = mock()
    val repository = ProgressRepository(mockDao, mock())
    
    // Act
    repository.getProgress("topic1")
    
    // Assert
    verify(exactly = 1) { mockDao.getProgress("topic1") }
}
```

### Example 3: Instrumented Test (Database)
```kotlin
@Test
fun database_insertProgress_persists() = runBlocking {
    val progress = Progress(topicId = "t1", bestScore = 90, totalQuestions = 10)
    
    progressDao.insertOrUpdate(progress)
    val retrieved = progressDao.getProgress("t1")
    
    assertEquals(90, retrieved?.bestScore)
}
```

### Example 4: UI Test
```kotlin
@Test
fun homeScreen_withTopics_displaysList() {
    composeTestRule.setContent {
        HomeScreen(viewModel = mockViewModel, onTopicClick = {})
    }
    
    composeTestRule.onNodeWithText("Topic 1").assertIsDisplayed()
}
```

## Test Maintenance

### Updating Tests for Code Changes
1. Run `./gradlew test` to identify broken tests
2. Update test expectations or mock setups
3. Ensure test names still accurately describe behavior
4. Keep tests simple and focused

### Removing Obsolete Tests
- Remove tests for deleted features
- Consolidate duplicate test coverage
- Update deprecated test patterns

### Performance Considerations
- Keep unit tests fast (< 1 second each)
- Group slow instrumented tests
- Use @Before/@After for test setup/cleanup

## Continuous Integration

### GitHub Actions Integration
Tests run automatically on:
- ✅ Every push to any branch
- ✅ Every pull request
- ✅ Manual workflow trigger

**Build Workflow:**
```yaml
- Run unit tests
- Run instrumented tests
- Fail if tests don't pass
```

## Coverage

Coverage isn't measured yet. Adding Kover (or JaCoCo) to CI would be the first step before setting a target.

## Troubleshooting Tests

### Common Issues

**"Test failed: Connection timeout"**
- Ensure emulator/device is running
- Check device connectivity
- Rebuild and try again

**"Mocking issue: Cannot mock final class"**
- mockito-kotlin 5 uses the inline mock maker, which mocks final classes
- Make sure the test uses `org.mockito.kotlin.mock`, not a hand-rolled Mockito setup

**"Database locked"**
- Ensure @After tearDown closes database
- Check for concurrent test execution

**"Compose test not finding element"**
- Use `composeTestRule.onRoot()` to verify render
- Check element's content-description or text
- Verify element is actually rendered

## Resources

- [Android Testing Documentation](https://developer.android.com/training/testing)
- [Jetpack Compose Testing](https://developer.android.com/jetpack/compose/testing)
- [Room Testing](https://developer.android.com/training/data-storage/room/testing-db)
- [JUnit Documentation](https://junit.org/)
- [Mockito-Kotlin](https://github.com/mockito/mockito-kotlin)

---

**See also:**
- [DEVELOPMENT.md](DEVELOPMENT.md) - Developer setup
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design

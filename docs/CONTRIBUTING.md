# Contributing Guidelines

## Welcome! 👋

Thank you for your interest in contributing to CircuitQueest! This guide will help you understand how to contribute effectively.

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the code, not the person
- Help others learn and grow

## Getting Started

### Fork & Clone
```bash
# Fork on GitHub, then:
git clone https://github.com/your-username/CircuitQueest.git
cd CircuitQueest
git remote add upstream https://github.com/original-repo/CircuitQueest.git
```

### Create Feature Branch
```bash
git checkout -b feature/my-feature
# or
git checkout -b fix/bug-fix
```

## Development Process

### 1. Code Changes
- Follow code style (see below)
- Write clear, descriptive commits
- Keep commits focused (one feature per commit)

### 2. Testing
All changes must include tests:

```bash
# Unit tests for logic changes
./gradlew test

# Instrumented tests for UI changes
./gradlew connectedAndroidTest

# Ensure tests pass before pushing
```

### 3. Code Quality
```bash
# Run formatters
./gradlew ktlintFormat

# Run quality checks
./gradlew detekt ktlint

# Fix any issues
```

### 4. Git Commit
```bash
git add .
git commit -m "feat: Clear description of changes"
```

### 5. Push & Pull Request
```bash
git push origin feature/my-feature
# Create PR on GitHub
```

## Code Style

### Kotlin Conventions
- Follow [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4-space indentation
- Max line length: 120 characters
- Use `ktlintFormat` for auto-formatting

### Naming
```kotlin
class MyClass                    // Classes: PascalCase
fun myFunction()                 // Functions: camelCase
val myVariable = value           // Variables: camelCase
const val MY_CONSTANT = 42       // Constants: UPPER_SNAKE_CASE
private val _privateVar          // Private: with underscore
```

### MVVM Pattern
- ViewModel for state management
- Repository for business logic
- Keep Composables simple and pure
- Use Hilt for dependency injection

### Comments
```kotlin
// Good: Explains WHY, not WHAT
// We store best score separately to handle race conditions
val bestScore = calculateBest(attempts)

// Avoid: Obvious comments
// Increment counter
counter++
```

## Testing Requirements

### Minimum Coverage
- **Unit tests** for all business logic
- **Edge case tests** for boundary conditions
- **Integration tests** for workflows
- **UI tests** for new screens/components

### Test Template
```kotlin
@Test
fun feature_condition_expectedResult() {
    // Arrange: Set up test data
    
    // Act: Execute the code
    
    // Assert: Verify the result
}
```

### Running Tests
```bash
# Before committing
./gradlew test

# Full suite (including instrumented)
./gradlew test connectedAndroidTest
```

## Pull Request Process

### PR Title Format
```
[TYPE] Description

Examples:
[feat] Add quiz retry functionality
[fix] Fix progress calculation bug
[docs] Update testing guide
[refactor] Simplify scoring logic
```

### PR Description Template
```markdown
## Description
Brief description of changes

## Changes
- Change 1
- Change 2
- Change 3

## Testing
- [ ] Added unit tests
- [ ] Added instrumented tests
- [ ] Ran full test suite: ./gradlew test connectedAndroidTest
- [ ] Checked code quality: ./gradlew detekt ktlint

## Related Issues
Fixes #123
Related to #456
```

### PR Checklist
- [ ] Code follows style guidelines
- [ ] Tests added/updated
- [ ] All tests pass locally
- [ ] Code quality checks pass
- [ ] Documentation updated (if needed)
- [ ] No breaking changes
- [ ] Commit messages are clear

## Types of Contributions

### Bug Fixes
1. Create issue describing the bug
2. Create branch: `fix/bug-name`
3. Fix the bug
4. Add regression test
5. Open PR with reference to issue

### Features
1. Discuss in issue or discuss first
2. Create branch: `feature/feature-name`
3. Implement feature
4. Add comprehensive tests
5. Update documentation
6. Open PR

### Documentation
1. Create branch: `docs/improvement-name`
2. Update docs
3. Open PR

### Tests
1. Create branch: `test/coverage-improvement`
2. Add tests for untested code
3. Open PR

## Commit Message Guidelines

### Format
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Example
```
feat(quiz): Add retry functionality

Users can now retry failed quizzes to improve their score.
The app tracks the best score across all attempts.

- Add retry button to result screen
- Persist all attempts in database
- Update best score logic

Fixes #123
```

### Types
- **feat**: New feature
- **fix**: Bug fix
- **docs**: Documentation
- **style**: Code style changes
- **refactor**: Code refactoring
- **test**: Test additions/changes
- **chore**: Build, CI/CD changes

## Code Review Guidelines

### Reviewing PRs
- Check code quality and style
- Verify tests are comprehensive
- Look for performance issues
- Suggest improvements respectfully

### Responding to Reviews
- Address all feedback
- Re-request review after changes
- Ask questions if unclear

## Development Tips

1. **Small PRs** - Easier to review (< 400 lines)
2. **Clear commit history** - Use descriptive messages
3. **One feature per PR** - Avoid mixing concerns
4. **Test first** - Write tests with your code
5. **Document changes** - Update docs if needed

## Project Structure

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed structure.

Quick overview:
```
app/src/main/java/com/circuitqueest/app/
├── ui/          # UI components & screens
├── viewmodel/   # State management
├── data/        # Repository, DAOs, database
├── navigation/  # Navigation setup
└── util/        # Utilities
```

## Questions?

- Check existing documentation in `docs/`
- Search closed GitHub issues
- Open a new issue with your question
- Join community discussions

## Recognition

Contributors are recognized in:
- Git commit history
- GitHub contributors page
- Project README (for significant contributions)

Thank you for making CircuitQueest better! 🚀

---

**Resources:**
- [DEVELOPMENT.md](DEVELOPMENT.md) - Development setup
- [TESTING.md](TESTING.md) - Testing guide
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design

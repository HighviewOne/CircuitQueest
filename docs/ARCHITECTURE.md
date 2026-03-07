# Architecture Guide

## System Design Overview

CircuitQueest uses modern Android architecture principles combining MVVM (Model-View-ViewModel) with Clean Architecture layers for separation of concerns, testability, and maintainability.

## Architecture Layers

### 1. Presentation Layer (UI)
**Location:** `app/src/main/java/com/circuitqueest/app/ui/`

- **Composable Functions** - Pure UI components (HomeScreen, LessonScreen, QuizScreen, ResultScreen)
- **State Management** - UI state flows from ViewModels via StateFlow/LiveData
- **User Interactions** - Button clicks, text input, navigation events
- **Navigation** - Jetpack Navigation Compose with type-safe routes

**Key Files:**
- `screens/` - Screen-level Composables
- `components/` - Reusable UI components
- `theme/` - Color schemes, typography, shapes

### 2. ViewModel Layer (State Management)
**Location:** `app/src/main/java/com/circuitqueest/app/viewmodel/`

**Why ViewModels?**
- Survive configuration changes (device rotation, etc.)
- Observable state for UI reactivity
- Decoupled from lifecycle details
- Facilitates testing with mocking

**Key ViewModels:**
- `HomeViewModel` - Topic list, filtering, XP tracking
- `LessonViewModel` - Lesson content display, navigation
- `QuizViewModel` - Quiz state, question flow, scoring
- `SavedStateHandle` - Route arguments (topicId, etc.)

**Hilt Integration:**
```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val topicsService: TopicsService
) : ViewModel()
```

### 3. Repository Layer (Business Logic)
**Location:** `app/src/main/java/com/circuitqueest/app/data/repository/`

**Pattern:** Single Source of Truth (SSOT)
- All data access through Repository
- Abstracts data sources (database, network, cache)
- Handles data consistency & conflict resolution

**ProgressRepository:**
- `getProgress(topicId)` - Fetch topic progress
- `getAllProgress()` - All user progress
- `saveQuizResult(result)` - Save quiz completion
- `getTotalXp()` - Calculate cumulative XP

### 4. Data Layer
**Location:** `app/src/main/java/com/circuitqueest/app/data/`

#### Database (Room)
- **Entities:** Progress, QuizResult
- **DAOs:** ProgressDao, QuizResultDao
- **Database:** AppDatabase (SQLite)

**Schema:**
```
Progress
  ├─ topicId (PK)
  ├─ bestScore (0-100)
  ├─ totalQuestions
  └─ lastAttempt (timestamp)

QuizResult
  ├─ id (PK, auto-increment)
  ├─ topicId (FK)
  ├─ score
  ├─ totalQuestions
  └─ timestamp
```

#### Content Layer
- **Topics:** 41 educational topics with lessons & quizzes
- **TopicsService:** Singleton holding all topics sorted by order
- **Content Modules:** OhmsLawContent, KirchhoffsContent, etc.

### 5. DI Layer (Dependency Injection with Hilt)
**Location:** `app/src/main/java/com/circuitqueest/app/data/di/`

**RepositoryModule:**
```kotlin
@Provides
@Singleton
fun provideProgressRepository(
    progressDao: ProgressDao,
    quizResultDao: QuizResultDao
): ProgressRepository = ProgressRepository(progressDao, quizResultDao)
```

**Benefits:**
- ✅ Loose coupling between components
- ✅ Easy to mock for testing
- ✅ No manual Factory classes needed
- ✅ Lifecycle-aware scoping (Singleton, ViewModel, etc.)

## Data Flow

### Quiz Completion Flow
```
User completes quiz
    ↓
QuizViewModel calculates score
    ↓
Repository.saveQuizResult(result)
    ↓
ProgressDao updates progress table
    ↓
QuizResultDao inserts result record
    ↓
HomeViewModel observes changes via Flow
    ↓
UI updates with new progress
```

## Component Interactions

### Navigation Flow
```
HomeScreen (topic list)
    ↓ [tap topic]
LessonScreen (content)
    ↓ [start quiz]
QuizScreen (questions)
    ↓ [submit]
ResultScreen (score)
    ↓ [next]
HomeScreen (updated progress)
```

## Key Architectural Decisions

### 1. MVVM + Clean Architecture
**Why:** Clear separation of concerns, testability, reusability

### 2. Single Repository Pattern
**Why:** One source of truth for all data, consistent state

### 3. Hilt for DI
**Why:** Type-safe, compile-time checking, Android-aware scoping

### 4. Room for Persistence
**Why:** Type-safe database access, migration support, built-in querying

### 5. Jetpack Compose for UI
**Why:** Modern declarative UI, better performance, easier state management

### 6. Offline-First
**Why:** No network dependency, instant load times, data privacy

## Module Structure

```
app/src/main/java/com/circuitqueest/app/
├── ui/
│   ├── screens/        (Composables for each screen)
│   ├── components/     (Reusable components)
│   └── theme/          (Colors, typography, shapes)
├── viewmodel/          (MVVM state management)
├── data/
│   ├── repository/     (Business logic layer)
│   ├── db/            (Room database)
│   │   ├── dao/       (Data access objects)
│   │   └── entity/    (Data models)
│   ├── content/       (Educational content)
│   └── di/            (Dependency injection)
├── util/              (Utility functions)
├── navigation/        (NavGraph definitions)
├── CircuitQuestApplication.kt (@HiltAndroidApp)
└── MainActivity.kt    (Entry point)
```

## Testing Architecture

### Unit Tests
- Pure Kotlin logic (scoring, calculations)
- Mocked dependencies via Mockito
- Repository layer logic
- ViewModel state management

### Instrumented Tests
- Real Room database operations
- UI component rendering (Compose)
- End-to-end workflows
- Database integrity validation

## Performance Considerations

### Optimization Techniques
1. **Lazy Loading** - Topics load on-demand
2. **Flow/StateFlow** - Efficient state updates
3. **Room Indexing** - Fast database queries
4. **ProGuard R8** - Code obfuscation & minification (20-40% APK reduction)
5. **Coroutines** - Non-blocking database operations

### Scalability
- Supports 41+ topics without performance degradation
- Database designed for 1000+ quiz attempts
- UI efficiently handles large lists via LazyColumn

## Security Considerations

1. **No Network Requests** - All data local, no external dependencies
2. **Code Obfuscation** - ProGuard R8 enabled in release builds
3. **Database Encryption** - Consider Room's encryption support for future versions
4. **Input Validation** - All user inputs validated before storage

## Future Architectural Improvements

- [ ] Network layer for sync/backup
- [ ] Cloud-based progress storage
- [ ] Local caching layer (RemoteMediator)
- [ ] Real-time leaderboards
- [ ] A/B testing framework
- [ ] Analytics layer
- [ ] Feature flags system

---

**For more details, see:**
- [TESTING.md](TESTING.md) - Testing strategy
- [DEVELOPMENT.md](DEVELOPMENT.md) - Developer setup
- [API.md](API.md) - Public APIs

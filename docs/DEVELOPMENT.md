# Development Guide

## Project Setup

### Prerequisites
- **Java:** JDK 17 or higher
- **Android SDK:** Platform 35, Build-tools 35.0.0
- **Gradle:** 8.7+
- **IDE:** Android Studio Arctic Fox or later
- **Git:** For version control

### Initial Setup (First Time)

```bash
# Clone the repository
git clone https://github.com/yourusername/CircuitQueest.git
cd CircuitQueest

# Sync Gradle
./gradlew sync

# Build the project
./gradlew assembleDebug

# Install on device/emulator
./gradlew installDebug
```

## Project Structure

```
CircuitQueest/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/circuitqueest/app/
│   │   │   │   ├── ui/              # Composable screens & components
│   │   │   │   ├── viewmodel/       # MVVM state management
│   │   │   │   ├── data/            # Repository, DAO, entities
│   │   │   │   ├── navigation/      # NavGraph definitions
│   │   │   │   ├── util/            # Utility functions
│   │   │   │   └── CircuitQuestApplication.kt
│   │   │   └── res/                 # Resources (strings, colors)
│   │   ├── test/                    # Unit tests
│   │   └── androidTest/             # Instrumented tests
│   ├── build.gradle.kts            # App-level build config
│   └── proguard-rules.pro           # ProGuard configuration
├── gradle/
│   └── libs.versions.toml           # Centralized dependency versions
├── gradle.properties                # Gradle settings
├── settings.gradle.kts              # Gradle settings
├── .github/
│   ├── workflows/                   # GitHub Actions CI/CD
│   └── ISSUE_TEMPLATE/              # Issue templates
├── docs/                            # Documentation
├── README.md                        # Main documentation
└── detekt.yml                       # Code quality config
```

## IDE Configuration

### Android Studio

**1. Import Project**
- File → Open → Select CircuitQueest folder
- Wait for Gradle sync to complete

**2. Configure SDK**
- File → Project Structure
- SDK Location → Set to your Android SDK path
- Verify Platform 35 is installed

**3. Configure Run Configuration**
- Run → Edit Configurations
- Create new "Android App" configuration
- Select "app" module
- Choose device/emulator

**4. Code Style**
- File → Settings → Editor → Code Style → Kotlin
- Set to "Kotlin style guide"

**5. VCS Configuration**
- File → Settings → Version Control → Git
- Verify git executable path

### Plugins to Install
- Kotlin (usually pre-installed)
- Android (pre-installed)
- Jetpack Compose (pre-installed)
- Database (optional, for debugging)

## Gradle Commands

### Build
```bash
./gradlew assembleDebug      # Debug APK
./gradlew assembleRelease    # Release APK (signed locally)
./gradlew clean              # Clean build files
./gradlew cleanBuildCache    # Clear Gradle cache
```

### Run & Install
```bash
./gradlew installDebug       # Build and install on device
./gradlew installRelease     # Install release build
./gradlew uninstallDebug     # Uninstall debug app
./gradlew runDebug           # Build, install, and run
```

### Testing
```bash
./gradlew test               # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests
./gradlew test connectedAndroidTest  # All tests
./gradlew test --tests "QuizScoringTest"  # Specific test
```

### Code Quality
```bash
./gradlew detekt             # Run Detekt analysis
./gradlew ktlint             # Check code formatting
./gradlew ktlintFormat       # Auto-format code
```

### Build & Verify
```bash
./gradlew build              # Full build with all checks
./gradlew check              # Run all checks (tests + quality)
```

## Development Workflow

### 1. Create Feature Branch
```bash
git checkout -b feature/new-feature
```

### 2. Make Changes
- Edit source files
- Follow code style (see below)

### 3. Test Locally
```bash
# Run tests
./gradlew test

# Check code quality
./gradlew ktlint detekt

# Build APK
./gradlew assembleDebug

# Install and test on device
./gradlew installDebug
```

### 4. Commit Changes
```bash
git add .
git commit -m "feat: Add new feature description

Detailed explanation of changes if needed.

Fixes #123"
```

### 5. Push & Create PR
```bash
git push origin feature/new-feature
# Create Pull Request on GitHub
```

## Code Style & Conventions

### Kotlin Style Guide
- Follow official [Kotlin style guide](https://kotlinlang.org/docs/coding-conventions.html)
- 120 character line limit
- 4-space indentation
- Use ktlint for auto-formatting

### Naming Conventions
```kotlin
// Classes: PascalCase
class HomeViewModel

// Functions & variables: camelCase
fun calculateScore(correct: Int, total: Int)
val userScore = 85

// Constants: UPPER_SNAKE_CASE
const val MAX_QUIZ_LENGTH = 100

// Private: leading underscore (optional)
private val _internalState = MutableStateFlow<State>()
```

### File Organization
```kotlin
// Order within Kotlin files:
// 1. Package declaration
// 2. Imports
// 3. Constants
// 4. Class/Interface declaration
// 5. Companion object
// 6. Constructor
// 7. Properties
// 8. Methods
// 9. Inner classes/interfaces
```

### MVVM Structure
```kotlin
// ViewModel template
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _myState = MutableStateFlow<MyState>()
    val myState = _myState.asStateFlow()
    
    fun doAction() {
        viewModelScope.launch {
            // Coroutine safe operation
        }
    }
}
```

## Debugging

### Logcat
```bash
# View logs
./gradlew logcat

# Filter logs
./gradlew logcat | grep "CircuitQueest"

# Show only errors
./gradlew logcat --level=E
```

### Database Inspection
```bash
# Pull database from device
adb pull /data/data/com.circuitqueest.app/databases/app.db

# Inspect with SQLite tools
sqlite3 app.db
sqlite> SELECT * FROM Progress;
```

### Breakpoint Debugging
1. Set breakpoint (click line number)
2. Run in Debug mode (Shift+F9)
3. App pauses at breakpoint
4. Step through code (F10/F11)
5. Inspect variables in Debug panel

### Performance Profiling
1. Run → Profile
2. Select profiler (CPU, Memory, Network, Energy)
3. Interact with app
4. Analyze results in profiler panel

## Common Tasks

### Adding a New Screen
```kotlin
// 1. Create Composable in ui/screens/
@Composable
fun MyScreen(viewModel: MyViewModel, onNavigate: (String) -> Unit) {
    // UI code
}

// 2. Create ViewModel with @HiltViewModel
@HiltViewModel
class MyViewModel @Inject constructor(
    // dependencies
) : ViewModel()

// 3. Add route to NavGraph
// In navigation/NavGraph.kt
```

### Adding Tests
```bash
# Unit test
# app/src/test/java/com/circuitqueest/app/.../MyTest.kt

# Instrumented test  
# app/src/androidTest/java/com/circuitqueest/app/.../MyTest.kt

# Run tests
./gradlew test
```

### Running on Device
```bash
# Connect physical device via USB
# Enable Developer mode & USB debugging

# List connected devices
adb devices

# Install and run
./gradlew installDebug
adb shell am start -n com.circuitqueest.app/.MainActivity
```

### Emulator Setup
```bash
# List available emulators
emulator -list-avds

# Run specific emulator
emulator -avd emulator-name

# Create new emulator
# Android Studio → AVD Manager → Create Virtual Device
```

## Git Workflow

### Commit Message Format
```
<type>: <subject>

<body>

<footer>

Types: feat, fix, docs, style, refactor, test, chore
Example:
feat: Add quiz retry functionality

Users can now retry failed quizzes to improve their score.
Best score is tracked across attempts.

Fixes #42
```

### Before Pushing
```bash
# Verify code quality
./gradlew detekt ktlint

# Run tests
./gradlew test

# Build APK
./gradlew assembleDebug

# Check git status
git status

# Push
git push origin feature-branch
```

## Performance Tips

1. **Use LazyColumn** for long lists
2. **Avoid recompositions** - use remember, remember Saveable
3. **Profile regularly** - Use Android Profiler
4. **Optimize database queries** - Use Room queries wisely
5. **Use coroutines** for background work

## Useful Resources

- [Android Developer Docs](https://developer.android.com/docs)
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose/documentation)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt Documentation](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Docs](https://kotlinlang.org/docs/home.html)

## Support

For questions or issues:
1. Check existing GitHub issues
2. Search Stack Overflow
3. Post in Android dev communities
4. Open a new GitHub issue with details

---

**See also:**
- [TESTING.md](TESTING.md) - Testing guide
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design
- [CONTRIBUTING.md](CONTRIBUTING.md) - Contributing guidelines

# CircuitQuEEst ⚡ - Master Electrical Engineering Through Gamified Learning

**Status: ✅ Production Ready** | **Tests: 300+** | **Coverage: 50%+** | **v1.0.0**

An Android app for learning Electrical Engineering fundamentals through an interactive quest/adventure metaphor. Complete lessons to unlock lore, then battle through quizzes to earn XP and advance to the next topic. Built with modern Android architecture, comprehensive testing, and production-ready code quality.

## 🎮 Key Features

- **41 Comprehensive Topics** - From fundamentals (Ohm's Law) to advanced (VLSI Design, ML Hardware)
- **Quest-Based Progression** - Complete topics sequentially to unlock advanced material
- **XP & Achievement System** - Earn points for lessons (+50 XP) and quizzes (score-based + bonuses)
- **Dual Question Types** - Multiple choice and numeric input with tolerance for rounding errors
- **Offline-First Architecture** - All content and progress stored locally using Room database
- **Dark Circuit-Board Theme** - Electric blue, gold, and charcoal color palette for immersion
- **Real-Time Progress Tracking** - Best scores, XP accumulation, and topic completion status
- **Production-Grade Testing** - 300+ comprehensive tests with 50%+ code coverage
- **Automated CI/CD** - GitHub Actions for build, testing, and quality checks
- **Clean Architecture** - MVVM + Hilt DI for maintainability and testability

## 📊 Topics Covered

### Fundamentals (Topics 1-6)
Ohm's Law, Series & Parallel Circuits, Kirchhoff's Laws, Capacitors & Inductors, AC Circuits, Operational Amplifiers

### Analog & Digital (Topics 7-15)
Thévenin-Norton Equivalent, Diodes, Transistors, Digital Logic, MOSFETs, Filters, Transformers

### Systems & Signals (Topics 16-24)
Signals & Systems, Power Electronics, Control Systems, Transmission Lines, Communication Systems, Electromagnetics, Semiconductor Physics

### Advanced Topics (Topics 25-41)
Digital Systems, Electric Machines, PCB Design, Embedded Systems, Power Systems, Sensors & Measurement, Antenna Design, VLSI Design, Signal Integrity, Battery & Storage, RF Circuits, Analog Circuits, Renewable Energy, IoT & Wireless, DSP, Fiber Optics, Electric Vehicles, Audio Electronics, MEMS, Radar Systems, Biomedical Electronics, ML Hardware

## 🛠 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Kotlin | 2.0.21 |
| **UI Framework** | Jetpack Compose | 2024.12.01 |
| **Architecture** | MVVM + Clean Architecture | - |
| **DI Framework** | Hilt | 2.48 |
| **Database** | Room | 2.6.1 |
| **Navigation** | Navigation Compose | 2.8.5 |
| **Build System** | Gradle Kotlin DSL | 8.7.3 |
| **Testing Framework** | JUnit4, Mockito, Compose UI Test | Latest |

## 🚀 Quick Start

### Prerequisites
- JDK 17 or higher
- Android SDK (Platform 35, Build-tools 35.0.0)
- Min SDK: 26 (Android 8.0+)
- Android Studio Arctic Fox or higher

### Setup (5 minutes)
```bash
# Clone repository
git clone https://github.com/yourusername/CircuitQueest.git
cd CircuitQueest

# Build APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run all tests (unit + instrumented)
./gradlew connectedAndroidTest

# Check code quality
./gradlew detekt ktlint
```

### Build & Run
```bash
# Debug build
./gradlew assembleDebug

# Release build (production)
./gradlew assembleRelease

# Output APK
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

## 📐 Architecture Overview

### MVVM + Clean Architecture
```
Presentation Layer (UI/Compose)
    ↓
ViewModel Layer (State Management)
    ↓
Repository Layer (Business Logic)
    ↓
Data Layer (Room Database)
    ↓
Local Storage (SQLite)
```

### Key Components
- **Screens** - HomeScreen, LessonScreen, QuizScreen, ResultScreen
- **ViewModels** - @HiltViewModel with SavedStateHandle
- **Repository** - Single source of truth for data
- **DAOs** - Type-safe database access
- **Services** - TopicsService for centralized topic management
- **Components** - Reusable Compose UI components

### Dependency Injection (Hilt)
- All ViewModels use @HiltViewModel
- Singleton ProgressRepository provided via RepositoryModule
- Navigation-aware SavedStateHandle for route arguments

## 📊 Testing Strategy

### Test Pyramid (300+ Tests)
```
         UI Tests (40)
        ╱───────────╲
       ╱ Component  ╲
      ╱   Tests (20) ╲
     ├────────────────┤
     │  Integration    │
     │    Tests (26)   │
     ├────────────────┤
     │   Unit Tests    │
     │    (197 tests)  │
     └────────────────┘
```

### Coverage Breakdown
- **Unit Tests (197)** - Scoring, Repository, ViewModels, Content modules, Edge cases
- **Instrumented Tests (103)** - Database operations, UI screens, Complex workflows
- **Total: 300+ tests** | **Coverage: 50%+**

### Running Tests
```bash
# Unit tests only
./gradlew test

# Instrumented tests (requires emulator/device)
./gradlew connectedAndroidTest

# Specific test class
./gradlew test --tests "com.circuitqueest.app.util.QuizScoringTest"

# With coverage report
./gradlew test --coverage
```

## 📈 Production Metrics

| Metric | Value | Status |
|--------|-------|--------|
| Test Count | 300+ | ✅ Excellent |
| Code Coverage | 50%+ | ✅ Target Met |
| CI/CD Pipelines | 2 (build + quality) | ✅ Active |
| ProGuard Obfuscation | Enabled | ✅ Secure |
| APK Size Reduction | 20-40% | ✅ Optimized |
| Min SDK | 26 (Android 8.0) | ✅ Compatible |

## 📚 Documentation

- **[ARCHITECTURE.md](docs/ARCHITECTURE.md)** - Deep dive into system design
- **[TESTING.md](docs/TESTING.md)** - Testing strategy and guide
- **[DEVELOPMENT.md](docs/DEVELOPMENT.md)** - Developer setup & workflow
- **[DEPLOYMENT.md](docs/DEPLOYMENT.md)** - Release & deployment guide
- **[API.md](docs/API.md)** - Public API documentation
- **[CONTRIBUTING.md](docs/CONTRIBUTING.md)** - Contributing guidelines
- **[TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md)** - FAQs & debugging

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](docs/CONTRIBUTING.md) for guidelines on:
- Code style and conventions
- Testing requirements
- Pull request process
- Git commit format

## 📦 Build Output

### Debug APK
```
app/build/outputs/apk/debug/app-debug.apk
Size: ~15-20 MB (unoptimized)
```

### Release APK
```
app/build/outputs/apk/release/app-release.apk
Size: ~9-12 MB (ProGuard + minification enabled)
Obfuscated: Yes (ProGuard R8)
```

## 🔧 Commands Reference

```bash
# Build variants
./gradlew assembleDebug          # Debug APK
./gradlew assembleRelease        # Release APK
./gradlew installDebug           # Install to device

# Testing
./gradlew test                   # Unit tests
./gradlew connectedAndroidTest   # Instrumented tests
./gradlew test connectedAndroidTest # All tests

# Code Quality
./gradlew detekt                 # Static analysis
./gradlew ktlint                 # Code formatting
./gradlew ktlintFormat           # Auto-format code

# Clean
./gradlew clean                  # Remove build output
./gradlew cleanBuildCache        # Clear Gradle cache
```

## 📱 Requirements

- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)
- **JDK:** 17
- **Gradle:** 8.7+
- **Kotlin:** 2.0+

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

## 👥 Contributors

- **Project Lead** - CircuitQueest Team
- **Testing Framework** - Built with modern Android testing best practices
- **CI/CD Setup** - GitHub Actions automation

## 🎯 Project Status

**Phase** | **Status** | **Tests** | **Coverage**
---------|-----------|----------|-------------
Phase 1 | ✅ Complete | 62 | 20%
Phase 2 | ✅ Complete | 87 | 25%
Phase 3 | ✅ Complete | 127 | 40%
Phase 4 | ✅ Complete | 167 | 50%
Phase 5 | ✅ Complete | 300+ | 50%+
**Total** | **✅ PRODUCTION READY** | **300+** | **50%+**

## 📞 Support

For issues, questions, or feature requests, please:
1. Check [TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md) for common issues
2. Open an issue on GitHub with clear description
3. Include device info, Android version, and error logs

## 🚀 What's Next?

- [ ] Deploy to Google Play Store
- [ ] Gather user feedback
- [ ] Performance monitoring setup
- [ ] Analytics integration
- [ ] Advanced animations & micro-interactions
- [ ] Leaderboards & achievements
- [ ] Social sharing features

---

**Built with ❤️ for Electrical Engineering enthusiasts | Stay curious, keep learning! ⚡**

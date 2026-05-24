<div align="center">

# ⚡ CircuitQueest

**Master Electrical Engineering through gamified quests**

[![Build](https://github.com/HighviewOne/CircuitQueest/actions/workflows/build.yml/badge.svg)](https://github.com/HighviewOne/CircuitQueest/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-2.3-2480FF?labelColor=0A0E1A)](https://github.com/HighviewOne/CircuitQueest/releases)
[![Android](https://img.shields.io/badge/Android-8.0%2B-34E090?logo=android&logoColor=white&labelColor=0A0E1A)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white&labelColor=0A0E1A)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-2024.12-4285F4?logo=android&logoColor=white&labelColor=0A0E1A)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/license-MIT-F4B840?labelColor=0A0E1A)](LICENSE)

[**Website**](https://highviewone.github.io/CircuitQueest/) · [**Documentation**](https://highviewone.github.io/CircuitQueest/documentation.html) · [**Privacy**](https://highviewone.github.io/CircuitQueest/privacy.html)

</div>

---

CircuitQueest is an offline-first Android app that teaches electrical engineering through a quest-based progression system. Work through 42 topics — from Ohm's Law to ML Hardware — each with a structured lesson and a 7-question quiz. Earn XP, unlock new topics, and track your progress entirely on-device.

> **Theme:** Cockpit dark aesthetic — Space Grotesk + JetBrains Mono, electric blue/gold palette, PCB trace motifs, IEEE schematic icons, and a toggleable blueprint mode.

## Screenshots

| Home | Lesson | Quiz | Result |
|------|--------|------|--------|
| Collapsible categories · search · XP bar | PCB hero · parallax scroll · sticky CTA | Progress nodes · option feedback | Animated score ring · up-next card |

## Features

- **42 EE topics** spanning fundamentals through advanced — Ohm's Law, Kirchhoff, MOSFETs, VLSI, RF, ML Hardware, and more
- **Quest progression** — complete a topic's quiz to unlock the next; no internet required
- **XP system** — +50 XP per lesson, score-based quiz XP, +100 first-completion bonus
- **Dual question types** — multiple choice and numeric input with tolerance-aware grading
- **Blueprint mode** — toggleable alternate palette (deep navy + white-28% borders)
- **Shared-element transitions** — topic cards morph into the lesson hero
- **Offline-first** — all content and progress in a local Room database; no account needed
- **Cockpit UI** — bundled Space Grotesk + JetBrains Mono, shimmer XP bar, pulsing progress nodes

## Topic Curriculum

<details>
<summary>All 42 topics across 9 categories</summary>

| # | Topic | Category |
|---|-------|----------|
| 1 | Ohm's Law | Fundamentals |
| 2 | Series & Parallel Circuits | Fundamentals |
| 3 | Kirchhoff's Laws | Fundamentals |
| 4 | Capacitors & Inductors | Fundamentals |
| 5 | AC Circuits | Fundamentals |
| 6 | Operational Amplifiers | Fundamentals |
| 7 | Thévenin & Norton | Analog |
| 8 | Diodes & Rectifiers | Analog |
| 9 | Transistors (BJTs) | Analog |
| 10 | Digital Logic Gates | Digital |
| 11 | MOSFETs | Analog |
| 12 | Filters | Analog |
| 13 | Transformers | Power |
| 14 | Signals & Systems | Systems |
| 15 | Power Electronics | Power |
| 16 | Control Systems | Systems |
| 17 | Transmission Lines | RF |
| 18 | Communication Systems | RF |
| 19 | Electromagnetics | Physics |
| 20 | Semiconductor Physics | Physics |
| 21 | Digital Systems | Digital |
| 22 | Electric Machines | Power |
| 23 | PCB Design | Hardware |
| 24 | Embedded Systems | Hardware |
| 25 | Power Systems | Power |
| 26 | Sensors & Measurement | Hardware |
| 27 | Antenna Design | RF |
| 28 | VLSI Design | Advanced |
| 29 | Signal Integrity & EMC | Advanced |
| 30 | Battery & Energy Storage | Advanced |
| 31 | RF Circuit Design | RF |
| 32 | Analog Circuit Design | Analog |
| 33 | Renewable Energy | Power |
| 34 | IoT & Wireless | Hardware |
| 35 | Digital Signal Processing | Systems |
| 36 | Fiber Optics & Photonics | Advanced |
| 37 | Electric Vehicles | Power |
| 38 | Audio Electronics | Analog |
| 39 | MEMS | Advanced |
| 40 | Radar Systems | RF |
| 41 | Biomedical Electronics | Advanced |
| 42 | Machine Learning Hardware | Advanced |

</details>

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0.21 |
| UI | Jetpack Compose (BOM 2024.12.01) |
| Architecture | MVVM · Hilt DI · Room |
| Navigation | Navigation Compose with shared-element transitions |
| Storage | Room 2.6.1 (offline-first, no backend) |
| Fonts | Space Grotesk + JetBrains Mono (bundled, fully offline) |
| Min SDK | 26 (Android 8.0) · Target SDK 35 |
| Build | Gradle 8.11.1 · AGP 8.7.3 · KSP |

## Quick Start

**Prerequisites:** JDK 17, Android SDK (platform 35)

```bash
git clone https://github.com/HighviewOne/CircuitQueest.git
cd CircuitQueest
./gradlew assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk  (~12 MB)
```

**Other tasks:**

```bash
./gradlew detekt          # Static analysis
./gradlew test            # Unit tests
./gradlew clean           # Clear build output
```

## Architecture

```
HomeScreen / LessonScreen / QuizScreen / ResultScreen
        ↓ (Hilt ViewModels)
HomeViewModel · LessonViewModel · QuizViewModel
        ↓ (StateFlow)
ProgressRepository
        ↓ (Room DAOs)
TopicProgress · QuizResult  (SQLite, local only)
```

Content is stored as Kotlin singleton objects in `data/content/` — no JSON, no API. Navigation uses `SharedTransitionLayout` for hero morphing between the topic list and the lesson screen.

## Adding a Topic

1. Create `app/src/main/java/com/circuitqueest/app/data/content/YourTopicContent.kt` — a singleton `object` with a `Topic`, 6+ `LessonSection`s, and a 7-question `Quiz`.
2. Add it to `TopicsService.allTopics`.
3. Done — sequential unlock and XP handling are automatic.

See any existing `*Content.kt` file for the exact shape.

## Contributing

Bug reports and content corrections are welcome. Please use the [issue templates](.github/ISSUE_TEMPLATE/) — there are forms for bugs, feature requests, and documentation improvements.

For code changes, open a PR against `master`. The build CI must pass.

## License

[MIT](LICENSE) © 2026 CircuitQueest

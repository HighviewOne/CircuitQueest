# CircuitQuEEst ⚡

An Android app for learning Electrical Engineering fundamentals through a quest/adventure metaphor. Complete lessons to unlock lore, then battle through quizzes to earn XP and advance to the next topic.

## Topics

| # | Topic | What You'll Learn |
|---|-------|-------------------|
| 1 | **Ohm's Law** | V=IR, power calculations, basic circuit analysis |
| 2 | **Series & Parallel Circuits** | Resistance combinations, voltage/current division |
| 3 | **Kirchhoff's Laws** | KCL, KVL, node and loop analysis |

Each topic includes a scrollable lesson with formulas and key concepts, followed by a 7-question quiz (multiple choice + numeric input).

## Features

- **Quest-based progression** - Complete topics sequentially to unlock the next challenge
- **XP system** - Earn points for completing lessons (+50 XP) and quizzes (score-based + bonuses)
- **Two question types** - Multiple choice and numeric input with tolerance for rounding
- **Offline-first** - All content and progress stored locally with Room database
- **Dark circuit-board theme** - Electric blue, gold, and charcoal color palette

## Tech Stack

- Kotlin + Jetpack Compose + Material 3
- MVVM architecture (ViewModel + Repository)
- Room database for local persistence
- Jetpack Navigation Compose
- Gradle Kotlin DSL with version catalog

## Requirements

- JDK 17
- Android SDK (platform 35, build-tools 35.0.0)
- Min SDK 26 (Android 8.0)

## Build

```bash
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

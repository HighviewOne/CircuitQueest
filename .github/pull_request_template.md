## What does this PR do?

<!-- One paragraph description. Link any related issues with "Fixes #NNN" -->

## Type of change

- [ ] Bug fix
- [ ] New topic / content addition
- [ ] UI / animation enhancement
- [ ] Refactor (no behavior change)
- [ ] Build / tooling

## Checklist

- [ ] `./gradlew assembleDebug` builds without errors
- [ ] `./gradlew detekt` passes with no new violations
- [ ] New topic follows `*Content.kt` format — `object`, 6+ `LessonSection`s, 7 questions, correct `order` integer
- [ ] No hardcoded colors — structural colors use `LocalCqPalette.current`; accent colors use `CqBlue / CqGold / CqGreen / CqRed` constants
- [ ] No new `@Preview` composables left in production files

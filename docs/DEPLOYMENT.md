# Deployment Guide

## Release Checklist

Before releasing to production:

- [ ] All tests passing: `./gradlew test connectedAndroidTest`
- [ ] Code quality checks passing: `./gradlew detekt ktlint`
- [ ] Version updated in build.gradle.kts
- [ ] CHANGELOG updated with new features
- [ ] Release notes prepared
- [ ] All deprecations removed
- [ ] Performance profiling completed
- [ ] Documentation updated
- [ ] Changelog committed

## Build Process

### Debug Build
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

**Release builds include:**
- ✅ ProGuard R8 code obfuscation
- ✅ Resource shrinking
- ✅ Minification (20-40% smaller APK)
- ✅ Release signing (configured locally)

## Version Management

### Semantic Versioning
Format: `MAJOR.MINOR.PATCH`

- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes

### Update Version
```
// In app/build.gradle.kts
defaultConfig {
    versionCode = 2      // Increment by 1
    versionName = "1.1.0" // Update version string
}
```

## Code Signing

### Local Signing Setup
```bash
# Create keystore (one-time)
keytool -genkey -v -keystore ~/.android/keystore.jks \
  -keyalg RSA -keysize 2048 -validity 10000 -alias circuitqueest

# Store credentials in local.properties
RELEASE_STORE_FILE=~/.android/keystore.jks
RELEASE_STORE_PASSWORD=your_password
RELEASE_KEY_ALIAS=circuitqueest
RELEASE_KEY_PASSWORD=your_key_password
```

### Configure Gradle
```kotlin
// app/build.gradle.kts
android {
    signingConfigs {
        release {
            storeFile = file(RELEASE_STORE_FILE)
            storePassword = RELEASE_STORE_PASSWORD
            keyAlias = RELEASE_KEY_ALIAS
            keyPassword = RELEASE_KEY_PASSWORD
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.release
        }
    }
}
```

## Play Store Submission

### Prepare Store Listing
1. **App Title** - "CircuitQuEEst ⚡"
2. **Short Description** (80 chars)
   - "Master electrical engineering through gamified learning"
3. **Full Description** (4,000 chars)
   - Features, screenshots, target audience
4. **Screenshots** (minimum 2, max 8)
   - Home screen, lesson screen, quiz screen
5. **Icon** (512x512 PNG)
6. **Feature Graphic** (1024x500 PNG)

### Release Steps
1. Create app listing in Google Play Console
2. Add APK (from `./gradlew assembleRelease`)
3. Review store listing
4. Add pricing (free, paid, or in-app)
5. Set content rating
6. Submit for review

### Testing Before Release
```bash
# Internal testing track
./gradlew bundleRelease
# Upload to Play Store internal testing track

# Staged rollout
# Start with 5% → 25% → 100% over time
```

## Build Variants

### Debug Variant
- Minimal obfuscation
- Debuggable
- Larger APK (~20 MB)
- Fast build time

### Release Variant
- Full obfuscation (ProGuard R8)
- Non-debuggable
- Smaller APK (~9-12 MB)
- Longer build time

## ProGuard Configuration

### Current Rules (app/proguard-rules.pro)
```
# Keep Kotlin metadata for reflection
-keep class kotlin.Metadata { *; }

# Keep Hilt-generated classes
-keep @dagger.hilt.** class * { *; }

# Keep Room entities
-keep @androidx.room.Entity class * { *; }

# Keep ViewModels
-keep @androidx.lifecycle.ViewModel class * { *; }
```

### Verification
```bash
# Check for warnings
./gradlew assembleRelease --warning
```

## Performance Optimization

### Already Implemented
✅ ProGuard R8 obfuscation  
✅ Resource shrinking  
✅ Code minification  
✅ Lazy loading UI  
✅ Efficient database queries  

### Monitoring
- Use Android Profiler for testing
- Monitor memory usage
- Track startup time
- Profile rendering performance

## Rollback Plan

If issues detected post-release:

1. **Identify issue** - Check crash reports, ANRs
2. **Prepare fix** - Test locally and in staging
3. **Create patch release** - Bump PATCH version
4. **Deploy patch** - Upload to Play Store
5. **Communicate** - Notify users of fix

## CI/CD Integration

### GitHub Actions Workflows
- **build.yml** - Runs on every push/PR
- **code-quality.yml** - Code quality checks

### Manual Release Steps
```bash
# 1. Update version
# 2. Update CHANGELOG
# 3. Commit and tag
git tag -a v1.1.0 -m "Release version 1.1.0"
git push origin v1.1.0

# 4. Build release APK
./gradlew assembleRelease

# 5. Upload to Play Store
# (Manual via Google Play Console)
```

## Monitoring & Analytics

### Recommended Tools
- **Firebase Crashlytics** - Crash reporting
- **Firebase Analytics** - User behavior
- **Google Play Console** - App statistics
- **Android Vitals** - Performance metrics

### Setup
```kotlin
// Add to MainActivity or CircuitQuestApplication
FirebaseAnalytics.getInstance(context).logEvent("app_opened", null)
```

## Beta Testing

### Play Store Beta Track
1. Upload APK to beta track
2. Select beta testers (Google Group)
3. Share test link: `https://play.google.com/apps/testing/com.circuitqueest.app`
4. Collect feedback for 1-2 weeks
5. Release to production if no critical issues

### Beta Testing Checklist
- [ ] All features working
- [ ] UI looks correct on different devices
- [ ] No crashes or ANRs
- [ ] Performance acceptable
- [ ] Database operations reliable
- [ ] Network (if applicable) stable

## Supported Devices

- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 35 (Android 15)
- **Recommended:** Android 10+ (SDK 29+)

### Device Testing
- Test on various screen sizes
- Test on different Android versions
- Test with different hardware (low/high RAM)

## Update Strategy

### Frequency
- Bug fixes: ASAP
- Features: Monthly/Quarterly
- Major updates: Semi-annually

### Backward Compatibility
- Always maintain min SDK support
- Test on older devices
- Gracefully handle deprecated APIs

## Documentation

### Update Documentation
- CHANGELOG.md - List changes
- README.md - Update feature list if needed
- docs/ - Update relevant guides
- Release notes - User-facing summary

### CHANGELOG Format
```markdown
## [1.1.0] - 2024-03-07

### Added
- Retry functionality for quizzes
- Dark mode support (optional future)

### Fixed
- Progress calculation bug (#123)
- UI rendering issue on older devices

### Changed
- Updated Kotlin to 2.0.21
```

## Post-Release

### Monitor
- Check crash reports daily
- Monitor ANRs and performance metrics
- Respond to user reviews

### Communicate
- Post release notes on GitHub
- Tweet/social about new features
- Update project documentation

### Iterate
- Plan next release based on feedback
- Create issues for improvements
- Prioritize bug fixes

---

**See also:**
- [DEVELOPMENT.md](DEVELOPMENT.md) - Development setup
- [TESTING.md](TESTING.md) - Testing guide
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design

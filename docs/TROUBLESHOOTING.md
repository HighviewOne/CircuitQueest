# Troubleshooting Guide

## Common Issues & Solutions

### Build Issues

#### "Gradle sync failed"
**Solution:**
```bash
./gradlew clean
./gradlew sync
# If persists, check Android SDK installation
```

#### "Unable to resolve dependency"
**Solution:**
```bash
./gradlew dependencies
# Check for conflicting versions in gradle/libs.versions.toml
# Clear Gradle cache: rm -rf ~/.gradle/caches
./gradlew build --refresh-dependencies
```

#### "Compilation error in generated Hilt code"
**Solution:**
- Verify @HiltAndroidApp on Application class
- Check all ViewModels have @HiltViewModel
- Clean build: `./gradlew clean build`
- Invalidate Android Studio cache: File → Invalidate Caches

---

### Runtime Issues

#### App crashes immediately on launch
**Solution:**
1. Check logcat: `adb logcat | grep CircuitQueest`
2. Look for java.lang.RuntimeException or similar
3. Common causes:
   - Missing @Inject constructors
   - Uninitialized Room database
   - Missing AndroidManifest permissions

#### "Unable to start MainActivity"
**Solution:**
```bash
# Check if @HiltAndroidApp is applied
# In CircuitQuestApplication.kt:
@HiltAndroidApp
class CircuitQuestApplication : Application()

# Check AndroidManifest.xml has correct application class
android:name=".CircuitQuestApplication"
```

#### Database locked error
**Solution:**
```bash
# Close all connections to database
# Restart emulator/device
./gradlew installDebug
adb shell pm clear com.circuitqueest.app
```

---

### Testing Issues

#### "Emulator not found"
**Solution:**
```bash
# List available emulators
emulator -list-avds

# Create new emulator
# Android Studio → AVD Manager → Create Virtual Device
# Or command line:
sdkmanager "system-images;android-35;google_apis;x86_64"
```

#### "Test fails with timeout"
**Solution:**
- Increase timeout in test annotation
- Check emulator is responsive
- Ensure device/emulator has enough storage
- Try restarting emulator: `adb reboot`

#### "Mockk mock verification fails"
**Solution:**
```kotlin
// Verify exact call count
verify(exactly = 1) { mockObject.method() }

// Use more flexible matching
verify(atLeast = 1) { mockObject.method() }
```

---

### UI/Compose Issues

#### "Composable not rendering"
**Solution:**
1. Check LazyColumn/Box parent layout
2. Verify modifiers (width/height constraints)
3. Check state updates via StateFlow
4. Use `composeTestRule.onRoot()` to verify render

#### "Text not displaying correctly"
**Solution:**
- Check maxLines parameter
- Verify text overflow setting
- Check Color visibility (dark text on dark background)
- Ensure font is loaded

#### "Navigation not working"
**Solution:**
```kotlin
// Verify route in NavGraph
composable("lesson/{topicId}") { backStackEntry ->
    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
    LessonScreen(topicId = topicId)
}

// Check navigation arguments passed correctly
navController.navigate("lesson/$topicId")
```

---

### Database Issues

#### "Database migration failed"
**Solution:**
- Check Room schema migration
- Clear app data: Settings → Apps → CircuitQuest → Storage → Clear
- Or: `adb shell pm clear com.circuitqueest.app`
- Rebuild and reinstall

#### "Data not persisting"
**Solution:**
```kotlin
// Ensure DAO operations use coroutines
override suspend fun insertOrUpdate(progress: Progress) {
    // Not blocking the main thread
}

// Check if using correct suspend functions
runBlocking { 
    progressDao.insertOrUpdate(progress)
}
```

#### "Query returns null"
**Solution:**
```kotlin
// Check if data exists
val all = progressDao.getAllProgress()

// Verify topicId matches exactly
val progress = progressDao.getProgress("exact_topic_id")

// Check database with adb
adb shell sqlite3 /data/data/com.circuitqueest.app/databases/app.db
sqlite> SELECT * FROM Progress;
```

---

### Performance Issues

#### App is slow/sluggish
**Solution:**
1. Use Android Profiler (Run → Profile)
2. Check for:
   - Main thread blocking (database, network)
   - Memory leaks
   - Excessive recompositions
3. Optimize:
   - Move heavy operations to viewModelScope.launch
   - Use remember for expensive calculations
   - Add .asStateFlow() for efficient state updates

#### High memory usage
**Solution:**
```kotlin
// Profile memory in Profiler
// Look for:
- Bitmap allocations
- String allocations
- Object collections

// Fix:
- Use smaller images
- Clear caches onCleared()
- Limit in-memory collections
```

#### Slow database queries
**Solution:**
```kotlin
// Add Room query indexes
@Query("SELECT * FROM Progress WHERE topicId = :topicId")
@Index("topicId")
suspend fun getProgress(topicId: String): Progress?

// Analyze with EXPLAIN QUERY PLAN
.schema
EXPLAIN QUERY PLAN SELECT * FROM Progress WHERE topicId = ?;
```

---

### Device/Emulator Issues

#### Emulator won't start
**Solution:**
```bash
# Check system images installed
sdkmanager --list

# Create fresh AVD
avdmanager create avd -n test_emulator -k "system-images;android-35;google_apis;x86_64"

# Start with verbose logging
emulator -avd test_emulator -verbose
```

#### "Permission denied" errors
**Solution:**
- Update permissions in AndroidManifest.xml
- Request runtime permissions for Android 6+
- Check app permissions: Settings → Apps → CircuitQuest → Permissions

#### Device not recognized by adb
**Solution:**
```bash
# List connected devices
adb devices

# If not showing:
adb kill-server
adb start-server
adb devices

# Check USB debugging is enabled
# Settings → Developer Options → USB Debugging
```

---

### Code Quality Issues

#### Detekt warnings
**Solution:**
```bash
# Run detekt to see all issues
./gradlew detekt

# Auto-fix formatting issues
./gradlew ktlintFormat

# Address remaining issues in code
```

#### ktlint formatting errors
**Solution:**
```bash
# Show formatting issues
./gradlew ktlint

# Auto-fix
./gradlew ktlintFormat

# Specific file
./gradlew ktlint --arguments '{0}'
```

---

### Git/Version Control Issues

#### "Failed to push"
**Solution:**
```bash
# Pull latest changes first
git pull origin main

# Resolve conflicts if any
# Then push
git push origin feature-branch
```

#### "Detached HEAD state"
**Solution:**
```bash
git checkout main
git pull origin main
# Or create new branch from HEAD
git checkout -b recovery-branch
```

---

## Debug Mode Setup

### Enable Verbose Logging
```kotlin
// In CircuitQuestApplication
if (BuildConfig.DEBUG) {
    Log.d("CircuitQueest", "Debug mode enabled")
}

// Add logging to key methods
override suspend fun getProgress(topicId: String): Progress? {
    Log.d("ProgressDao", "Getting progress for $topicId")
    return database.query(...)
}
```

### Logcat Filtering
```bash
# All logs from app
adb logcat | grep CircuitQueest

# Only errors
adb logcat | grep "E/CircuitQueest"

# Save logs to file
adb logcat > debug.log
```

## Performance Profiling

### Using Android Profiler
1. Run → Profile 'app'
2. Select profiler (CPU, Memory, Network)
3. Interact with app
4. Analyze results:
   - Look for ANRs
   - Check memory leaks
   - Monitor frame rate
   - Check network traffic

### Benchmark Tests
```kotlin
@Test
fun scoring_performance() {
    val start = System.currentTimeMillis()
    repeat(1000) {
        calculateScore(8, 10)
    }
    val duration = System.currentTimeMillis() - start
    assert(duration < 100) // Should be very fast
}
```

## Getting Help

1. **Check Documentation**
   - [DEVELOPMENT.md](DEVELOPMENT.md)
   - [TESTING.md](TESTING.md)
   - [ARCHITECTURE.md](ARCHITECTURE.md)

2. **Search Issues**
   - GitHub Issues with labels
   - Stack Overflow tags: android, kotlin, compose

3. **Debug Tools**
   - Android Studio Debugger
   - Android Profiler
   - Logcat
   - Database Inspector

4. **Ask for Help**
   - Create GitHub issue with:
     - Error message
     - Steps to reproduce
     - Device/emulator info
     - Android version
     - Logs

---

**For additional help:**
- Android Developer Documentation
- Stack Overflow
- GitHub Discussions
- Android Communities

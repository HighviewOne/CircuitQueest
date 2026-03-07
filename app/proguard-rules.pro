# Hilt
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }

# Keep data classes used by Room entities
-keepclassmembers class * {
    @androidx.room.* <fields>;
}

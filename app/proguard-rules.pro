# Room, Hilt, Compose, Kotlin and kotlinx ship their own consumer R8 rules in
# their AARs, so no blanket -keep is needed for them (those disabled most of
# R8's shrinking). Add a rule here only for code reached via reflection.

# Hilt
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

# Room (also covered by Room's consumer rules; kept as a belt-and-braces guard)
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

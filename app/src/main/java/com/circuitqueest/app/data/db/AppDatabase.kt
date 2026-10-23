package com.circuitqueest.app.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.circuitqueest.app.data.db.dao.MissedQuestionDao
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.dao.StudyDayDao
import com.circuitqueest.app.data.db.entity.MissedQuestion
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.StudyDay
import com.circuitqueest.app.data.db.entity.TopicProgress

/**
 * Bump [version] and add a Migration (tested with MigrationTestHelper against the
 * exported JSON under app/schemas/) whenever an entity changes. Without one, Room
 * throws on launch for every existing install.
 */
@Database(
    entities = [TopicProgress::class, QuizResult::class, MissedQuestion::class, StudyDay::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2), // v2.5: adds missed_questions (review mode)
        AutoMigration(from = 2, to = 3, spec = AppDatabase.BackfillStudyDays::class) // v2.6: streaks
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun missedQuestionDao(): MissedQuestionDao
    abstract fun studyDayDao(): StudyDayDao

    /** Seeds study_days from activity saved before streaks existed. */
    class BackfillStudyDays : AutoMigrationSpec {
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            backfillStudyDays(db)
        }
    }

    companion object {
        /** On-disk name; changing it orphans every existing player's progress. */
        const val DATABASE_NAME = "circuitqueest_db"

        /** Name used by v1.0–v2.0; [LegacyDatabaseImport] moves its progress into [DATABASE_NAME]. */
        const val LEGACY_DATABASE_NAME = "circuitqueest-db"

        /**
         * Adds a study day for every quiz attempt and each topic's last activity, in the
         * device's time zone (as [com.circuitqueest.app.util.Streaks.today] uses). Idempotent.
         */
        fun backfillStudyDays(db: SupportSQLiteDatabase) {
            val epochDay = "CAST(julianday(date(%s / 1000, 'unixepoch', 'localtime')) - 2440587.5 AS INTEGER)"
            db.execSQL(
                "INSERT OR IGNORE INTO study_days (epochDay) SELECT ${epochDay.format("timestamp")} " +
                    "FROM quiz_results WHERE timestamp > 0"
            )
            db.execSQL(
                "INSERT OR IGNORE INTO study_days (epochDay) SELECT ${epochDay.format("lastAccessedTimestamp")} " +
                    "FROM topic_progress WHERE lastAccessedTimestamp > 0"
            )
        }
    }
}

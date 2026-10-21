package com.circuitqueest.app.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.circuitqueest.app.data.db.dao.MissedQuestionDao
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.MissedQuestion
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress

/**
 * Bump [version] and add a Migration (tested with MigrationTestHelper against the
 * exported JSON under app/schemas/) whenever an entity changes. Without one, Room
 * throws on launch for every existing install.
 */
@Database(
    entities = [TopicProgress::class, QuizResult::class, MissedQuestion::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2) // v2.5: adds missed_questions (review mode)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun missedQuestionDao(): MissedQuestionDao

    companion object {
        /** On-disk name; changing it orphans every existing player's progress. */
        const val DATABASE_NAME = "circuitqueest_db"
    }
}

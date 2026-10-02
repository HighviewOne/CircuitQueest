package com.circuitqueest.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress

/**
 * Bump [version] and add a Migration (tested with MigrationTestHelper against the
 * exported JSON under app/schemas/) whenever an entity changes. Without one, Room
 * throws on launch for every existing install.
 */
@Database(
    entities = [TopicProgress::class, QuizResult::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        /** On-disk name; changing it orphans every existing player's progress. */
        const val DATABASE_NAME = "circuitqueest_db"
    }
}

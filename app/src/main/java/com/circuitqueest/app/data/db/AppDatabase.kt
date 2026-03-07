package com.circuitqueest.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress

@Database(
    entities = [TopicProgress::class, QuizResult::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "circuitqueest_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

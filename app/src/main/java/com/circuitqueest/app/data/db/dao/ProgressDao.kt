package com.circuitqueest.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.circuitqueest.app.data.db.entity.TopicProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId")
    fun getProgress(topicId: String): Flow<TopicProgress?>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId")
    suspend fun getProgressOnce(topicId: String): TopicProgress?

    @Query("SELECT * FROM topic_progress")
    fun getAllProgress(): Flow<List<TopicProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: TopicProgress)

    @Query("SELECT COALESCE(SUM(xpEarned), 0) FROM topic_progress")
    fun getTotalXp(): Flow<Int>
}

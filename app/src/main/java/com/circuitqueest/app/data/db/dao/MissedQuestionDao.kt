package com.circuitqueest.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.circuitqueest.app.data.db.entity.MissedQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface MissedQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(missed: MissedQuestion)

    @Query("DELETE FROM missed_questions WHERE topicId = :topicId AND questionId = :questionId")
    suspend fun delete(topicId: String, questionId: String)

    @Query("SELECT * FROM missed_questions ORDER BY missedAt")
    suspend fun getAll(): List<MissedQuestion>

    @Query("SELECT * FROM missed_questions WHERE topicId = :topicId ORDER BY missedAt")
    suspend fun getForTopic(topicId: String): List<MissedQuestion>

    @Query("SELECT COUNT(*) FROM missed_questions")
    fun count(): Flow<Int>
}

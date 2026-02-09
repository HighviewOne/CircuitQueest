package com.circuitqueest.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.circuitqueest.app.data.db.entity.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insertResult(result: QuizResult)

    @Query("SELECT * FROM quiz_results WHERE topicId = :topicId ORDER BY timestamp DESC")
    fun getResultsForTopic(topicId: String): Flow<List<QuizResult>>

    @Query("SELECT MAX(score) FROM quiz_results WHERE topicId = :topicId")
    fun getBestScore(topicId: String): Flow<Int?>
}

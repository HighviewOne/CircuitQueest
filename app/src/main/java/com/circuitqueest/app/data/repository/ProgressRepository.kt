package com.circuitqueest.app.data.repository

import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress
import kotlinx.coroutines.flow.Flow

class ProgressRepository(
    private val progressDao: ProgressDao,
    private val quizResultDao: QuizResultDao
) {
    fun getAllProgress(): Flow<List<TopicProgress>> = progressDao.getAllProgress()

    fun getProgress(topicId: String): Flow<TopicProgress?> = progressDao.getProgress(topicId)

    fun getTotalXp(): Flow<Int> = progressDao.getTotalXp()

    suspend fun markLessonCompleted(topicId: String) {
        val existing = getCurrentProgress(topicId)
        progressDao.upsertProgress(
            existing.copy(
                lessonCompleted = true,
                xpEarned = existing.xpEarned + if (!existing.lessonCompleted) 50 else 0,
                lastAccessedTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun saveQuizResult(topicId: String, score: Int, totalQuestions: Int) {
        quizResultDao.insertResult(
            QuizResult(
                topicId = topicId,
                score = score,
                totalQuestions = totalQuestions
            )
        )

        val existing = getCurrentProgress(topicId)
        val isNewBest = score > existing.bestScore
        val xpForQuiz = (score * 10) + if (!existing.quizCompleted) 100 else 0
        progressDao.upsertProgress(
            existing.copy(
                quizCompleted = true,
                bestScore = if (isNewBest) score else existing.bestScore,
                totalQuestions = totalQuestions,
                xpEarned = existing.xpEarned + xpForQuiz,
                lastAccessedTimestamp = System.currentTimeMillis()
            )
        )
    }

    private suspend fun getCurrentProgress(topicId: String): TopicProgress {
        return progressDao.getProgressOnce(topicId) ?: TopicProgress(topicId = topicId)
    }

    fun getQuizResults(topicId: String): Flow<List<QuizResult>> =
        quizResultDao.getResultsForTopic(topicId)
}

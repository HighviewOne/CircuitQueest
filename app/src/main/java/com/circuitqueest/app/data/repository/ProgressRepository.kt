package com.circuitqueest.app.data.repository

import com.circuitqueest.app.data.db.dao.ProgressDao
import com.circuitqueest.app.data.db.dao.QuizResultDao
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress
import com.circuitqueest.app.util.QuizScoring
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ProgressRepository(
    private val progressDao: ProgressDao,
    private val quizResultDao: QuizResultDao
) {
    // Progress updates are read-modify-write; serialize them so two writes to the
    // same topic (e.g. finishing a lesson while a quiz save is in flight) can't
    // overwrite each other's XP.
    private val writeLock = Mutex()

    fun getAllProgress(): Flow<List<TopicProgress>> = progressDao.getAllProgress()

    fun getProgress(topicId: String): Flow<TopicProgress?> = progressDao.getProgress(topicId)

    fun getTotalXp(): Flow<Int> = progressDao.getTotalXp()

    suspend fun markLessonCompleted(topicId: String) = writeLock.withLock {
        val existing = getCurrentProgress(topicId)
        progressDao.upsertProgress(
            existing.copy(
                lessonCompleted = true,
                xpEarned = existing.xpEarned +
                    if (!existing.lessonCompleted) QuizScoring.LESSON_XP else 0,
                lastAccessedTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun saveQuizResult(topicId: String, score: Int, totalQuestions: Int) {
        recordQuizResult(topicId, score, totalQuestions)
    }

    /**
     * Saves the attempt and returns the XP actually awarded for it. A topic only
     * counts as completed (unlocking the next one) once a quiz attempt passes.
     */
    suspend fun recordQuizResult(topicId: String, score: Int, totalQuestions: Int): Int =
        writeLock.withLock {
            quizResultDao.insertResult(
                QuizResult(
                    topicId = topicId,
                    score = score,
                    totalQuestions = totalQuestions
                )
            )

            val existing = getCurrentProgress(topicId)
            val passed = QuizScoring.isPassing(score, totalQuestions)
            val xpForQuiz = QuizScoring.calculateXp(
                score = score,
                previousBest = existing.bestScore,
                isFirstPass = passed && !existing.quizCompleted
            )
            progressDao.upsertProgress(
                existing.copy(
                    quizCompleted = existing.quizCompleted || passed,
                    bestScore = maxOf(score, existing.bestScore),
                    totalQuestions = totalQuestions,
                    xpEarned = existing.xpEarned + xpForQuiz,
                    lastAccessedTimestamp = System.currentTimeMillis()
                )
            )
            xpForQuiz
        }

    private suspend fun getCurrentProgress(topicId: String): TopicProgress {
        return progressDao.getProgressOnce(topicId) ?: TopicProgress(topicId = topicId)
    }

    fun getQuizResults(topicId: String): Flow<List<QuizResult>> =
        quizResultDao.getResultsForTopic(topicId)
}

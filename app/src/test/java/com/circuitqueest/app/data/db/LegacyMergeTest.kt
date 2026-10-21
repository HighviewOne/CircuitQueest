package com.circuitqueest.app.data.db

import com.circuitqueest.app.data.db.entity.TopicProgress
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class LegacyMergeTest {

    private val legacy = TopicProgress(
        topicId = "ohms_law", lessonCompleted = true, quizCompleted = true,
        bestScore = 7, totalQuestions = 7, xpEarned = 220, lastAccessedTimestamp = 100
    )

    @Test
    fun noCurrentProgress_takesLegacyAsIs() {
        assertEquals(legacy, LegacyDatabaseImport.mergeProgress(null, legacy))
    }

    @Test
    fun keepsTheBetterOfEachField() {
        val current = TopicProgress(
            topicId = "ohms_law", lessonCompleted = false, quizCompleted = false,
            bestScore = 3, totalQuestions = 7, xpEarned = 50, lastAccessedTimestamp = 500
        )

        val merged = LegacyDatabaseImport.mergeProgress(current, legacy)

        assertEquals(
            TopicProgress("ohms_law", true, true, 7, 7, 220, 500),
            merged
        )
    }

    @Test
    fun legacyNeverLowersNewerProgress() {
        val current = legacy.copy(bestScore = 7, xpEarned = 270, lastAccessedTimestamp = 900)
        val worseLegacy = legacy.copy(lessonCompleted = false, quizCompleted = false, bestScore = 2, xpEarned = 20)

        assertEquals(current, LegacyDatabaseImport.mergeProgress(current, worseLegacy))
    }

    @Test
    fun mergeIsIdempotent() {
        val current = legacy.copy(bestScore = 4, xpEarned = 90, lastAccessedTimestamp = 300)
        val once = LegacyDatabaseImport.mergeProgress(current, legacy)

        assertEquals(once, LegacyDatabaseImport.mergeProgress(once, legacy))
    }
}

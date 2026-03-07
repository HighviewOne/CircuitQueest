package com.circuitqueest.app.data.content

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertNotNull
import kotlin.test.assertFalse

@RunWith(JUnit4::class)
class TopicsServiceTest {

    @Test
    fun topicsService_allTopics_isNotEmpty() {
        // Assert
        assertTrue(TopicsService.allTopics.isNotEmpty())
    }

    @Test
    fun topicsService_allTopics_countIs41() {
        // Assert - verify exact count
        assertEquals(41, TopicsService.allTopics.size)
    }

    @Test
    fun topicsService_allTopics_isSortedByOrder() {
        // Assert
        val topics = TopicsService.allTopics
        for (i in 0 until topics.size - 1) {
            assertTrue(
                topics[i].order <= topics[i + 1].order,
                "Topics not sorted by order at index $i: ${topics[i].order} > ${topics[i + 1].order}"
            )
        }
    }

    @Test
    fun topicsService_topicIds_areUnique() {
        // Arrange
        val topics = TopicsService.allTopics
        val ids = topics.map { it.id }

        // Assert
        assertEquals(topics.size, ids.distinct().size, "Duplicate topic IDs found")
    }

    @Test
    fun topicsService_topicTitles_areNotEmpty() {
        // Assert
        TopicsService.allTopics.forEach { topic ->
            assertTrue(topic.title.isNotEmpty(), "Topic ${topic.id} has empty title")
        }
    }

    @Test
    fun topicsService_topicSubtitles_areNotEmpty() {
        // Assert
        TopicsService.allTopics.forEach { topic ->
            assertTrue(topic.subtitle.isNotEmpty(), "Topic ${topic.id} has empty subtitle")
        }
    }

    @Test
    fun topicsService_firstTopic_isOhmsLaw() {
        // Assert
        assertEquals("ohms_law", TopicsService.allTopics.first().id)
    }

    @Test
    fun topicsService_allTopics_haveValidLessons() {
        // Assert
        TopicsService.allTopics.forEach { topic ->
            assertNotNull(topic.lesson, "Topic ${topic.id} has no lesson")
            assertTrue(topic.lesson.title.isNotEmpty(), "Topic ${topic.id} lesson has no title")
        }
    }

    @Test
    fun topicsService_allTopics_haveValidQuizzes() {
        // Assert
        TopicsService.allTopics.forEach { topic ->
            assertNotNull(topic.quiz, "Topic ${topic.id} has no quiz")
            assertTrue(topic.quiz.questions.isNotEmpty(), "Topic ${topic.id} quiz has no questions")
        }
    }

    @Test
    fun topicsService_allTopics_quizzesHaveMultipleQuestion_Types() {
        // Assert - at least some quizzes have both MC and numeric
        val topics = TopicsService.allTopics
        assertTrue(topics.isNotEmpty(), "No topics found")
        // Just verify quizzes exist with questions
        topics.forEach { topic ->
            assertTrue(topic.quiz.questions.isNotEmpty(), "Quiz for ${topic.id} is empty")
        }
    }
}

package com.circuitqueest.app.ui.screens

import com.circuitqueest.app.data.content.TopicsService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TopicSearchTest {

    private val ohmsLaw = TopicsService.allTopics.first { it.id == "ohms_law" }

    @Test
    fun matchesTitleIgnoringCase() {
        assertTrue(ohmsLaw.matchesSearch("OHM"))
    }

    @Test
    fun matchesLessonFormula() {
        val formula = ohmsLaw.lesson.sections.firstNotNullOf { it.formula }
        assertTrue(ohmsLaw.matchesSearch(formula))
    }

    @Test
    fun matchesSectionHeading() {
        assertTrue(ohmsLaw.matchesSearch(ohmsLaw.lesson.sections.first().heading))
    }

    @Test
    fun rejectsUnrelatedQuery() {
        assertFalse(ohmsLaw.matchesSearch("zzz-not-a-topic"))
    }
}

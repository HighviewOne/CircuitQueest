package com.circuitqueest.app.util

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class StreaksTest {

    private val today = 20_000L

    @Test
    fun noStudyDays_isEmptyState() {
        assertEquals(StreakState(), Streaks.compute(emptyList(), today))
    }

    @Test
    fun studiedTodayOnly_isOneDay() {
        assertEquals(StreakState(current = 1, best = 1, studiedToday = true), Streaks.compute(listOf(today), today))
    }

    @Test
    fun consecutiveDaysEndingToday_countAll() {
        val days = listOf(today - 3, today - 2, today - 1, today)
        assertEquals(StreakState(current = 4, best = 4, studiedToday = true), Streaks.compute(days, today))
    }

    @Test
    fun streakEndingYesterday_isStillAlive() {
        val days = listOf(today - 2, today - 1)
        assertEquals(StreakState(current = 2, best = 2, studiedToday = false), Streaks.compute(days, today))
    }

    @Test
    fun missedYesterday_breaksStreak_butKeepsBest() {
        val days = listOf(today - 5, today - 4, today - 3, today - 2)
        assertEquals(StreakState(current = 0, best = 4, studiedToday = false), Streaks.compute(days, today))
    }

    @Test
    fun gapResetsCurrent_bestIsLongestRun() {
        val days = listOf(today - 10, today - 9, today - 8, today - 1, today)
        assertEquals(StreakState(current = 2, best = 3, studiedToday = true), Streaks.compute(days, today))
    }

    @Test
    fun unsortedAndDuplicateDays_areHandled() {
        val days = listOf(today, today - 1, today, today - 2, today - 1)
        assertEquals(StreakState(current = 3, best = 3, studiedToday = true), Streaks.compute(days, today))
    }

    @Test
    fun futureDays_doNotExtendCurrent() {
        // Clock moved backwards (time zone change): tomorrow's row shouldn't count as today.
        val days = listOf(today + 1)
        assertEquals(0, Streaks.compute(days, today).current)
    }
}

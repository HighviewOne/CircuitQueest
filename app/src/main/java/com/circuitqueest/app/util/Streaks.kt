package com.circuitqueest.app.util

import java.time.LocalDate

data class StreakState(
    /** Consecutive study days ending today, or yesterday if today has no study yet. */
    val current: Int = 0,
    val best: Int = 0,
    val studiedToday: Boolean = false
)

object Streaks {

    fun today(): Long = LocalDate.now().toEpochDay()

    /** [days] are epoch days in any order; duplicates are fine. */
    fun compute(days: Collection<Long>, today: Long): StreakState {
        if (days.isEmpty()) return StreakState()
        val set = days.toHashSet()
        val studiedToday = today in set
        // A streak survives until the end of the day after the last study day.
        var day = if (studiedToday) today else today - 1
        var current = 0
        while (day in set) {
            current++
            day--
        }

        var best = 0
        var run = 0
        var prev: Long? = null
        for (d in set.sorted()) {
            run = if (prev != null && d == prev + 1) run + 1 else 1
            best = maxOf(best, run)
            prev = d
        }
        return StreakState(current = current, best = best, studiedToday = studiedToday)
    }
}

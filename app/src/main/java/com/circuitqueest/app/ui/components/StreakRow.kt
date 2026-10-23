package com.circuitqueest.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextOverflow
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Spacing
import com.circuitqueest.app.util.StreakState

/**
 * Study streak under the XP bar. Gold once today counts; dim (with a nudge) while the
 * streak is still alive from yesterday. Hidden until the player has studied at least once.
 */
@Composable
fun StreakRow(streak: StreakState, modifier: Modifier = Modifier) {
    if (streak.best == 0) return
    val days = if (streak.current == 1) "day" else "days"
    val status = when {
        streak.studiedToday -> "Done for today"
        streak.current > 0 -> "Study today to keep it"
        else -> "Study today to start one"
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.s4)
            .clearAndSetSemantics {
                contentDescription =
                    "Study streak ${streak.current} $days, best ${streak.best}. $status"
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🔥 ${streak.current}-DAY STREAK · BEST ${streak.best}",
            style = MonoLabel,
            color = if (streak.studiedToday) CqGold else CqTextDim,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        if (!streak.studiedToday) {
            Text(
                text = status,
                style = MonoLabel,
                color = CqTextDim,
                maxLines = 1,
                modifier = Modifier.padding(start = Spacing.s8)
            )
        }
    }
}

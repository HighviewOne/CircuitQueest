package com.circuitqueest.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing

/**
 * Full-screen message with a Back button. Defaults cover a route naming a topic that
 * doesn't exist; review mode reuses it for an empty review queue.
 */
@Composable
fun QuestNotFound(
    onBack: () -> Unit,
    title: String = "Quest not found",
    message: String = "This quest isn't available. Head back to the quest map."
) {
    val pal = LocalCqPalette.current
    Scaffold(containerColor = pal.bg) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.s24),
            verticalArrangement = Arrangement.spacedBy(Spacing.s16, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = CqText
            )
            Text(
                text = message,
                fontFamily = SpaceGrotesk,
                fontSize = 15.sp,
                color = CqTextDim,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = CqBlue, contentColor = CqText)
            ) {
                Text("Back", fontFamily = SpaceGrotesk, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

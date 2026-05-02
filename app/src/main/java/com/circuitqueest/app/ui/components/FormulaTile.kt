package com.circuitqueest.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqSurface2
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.Spacing

@Composable
fun FormulaTile(
    formula: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.lg))
            .background(CqSurface2)
            .goldBrackets()
            .padding(Spacing.s24),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = formula,
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            color = CqText,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp
        )
    }
}


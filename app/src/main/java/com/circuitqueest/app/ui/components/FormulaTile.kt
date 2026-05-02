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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            .drawGoldBrackets()
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

private fun Modifier.drawGoldBrackets(): Modifier = drawBehind {
    val len = 18.dp.toPx()
    val inset = 10.dp.toPx()
    val sw = 1.5.dp.toPx()
    val c = CqGold
    val cap = StrokeCap.Square

    // Top-left
    drawLine(c, Offset(inset, inset + len), Offset(inset, inset), sw, cap)
    drawLine(c, Offset(inset, inset), Offset(inset + len, inset), sw, cap)
    // Top-right
    drawLine(c, Offset(size.width - inset - len, inset), Offset(size.width - inset, inset), sw, cap)
    drawLine(c, Offset(size.width - inset, inset), Offset(size.width - inset, inset + len), sw, cap)
    // Bottom-left
    drawLine(c, Offset(inset, size.height - inset - len), Offset(inset, size.height - inset), sw, cap)
    drawLine(c, Offset(inset, size.height - inset), Offset(inset + len, size.height - inset), sw, cap)
    // Bottom-right
    drawLine(c, Offset(size.width - inset, size.height - inset - len), Offset(size.width - inset, size.height - inset), sw, cap)
    drawLine(c, Offset(size.width - inset, size.height - inset), Offset(size.width - inset - len, size.height - inset), sw, cap)
}

package com.circuitqueest.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel

@Composable
fun TopicGlyphBadge(
    imageVector: ImageVector? = null,
    label: String = "",
    accentColor: Color = CqBlue,
    size: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    val pal = LocalCqPalette.current
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(pal.bg)
            .border(1.dp, accentColor.copy(alpha = 0.4f), shape),
        contentAlignment = Alignment.Center
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(size * 0.58f)
            )
        } else if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MonoLabel.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.36f).sp
                ),
                color = accentColor
            )
        }
    }
}

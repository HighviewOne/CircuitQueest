package com.circuitqueest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CircuitQueestColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = TextWhite,
    primaryContainer = ElectricBlueDark,
    onPrimaryContainer = TextWhite,
    secondary = GoldAmber,
    onSecondary = DarkCharcoal,
    secondaryContainer = GoldAmberDark,
    onSecondaryContainer = TextWhite,
    tertiary = CircuitGreen,
    onTertiary = DarkCharcoal,
    background = DarkCharcoal,
    onBackground = TextWhite,
    surface = Slate,
    onSurface = TextWhite,
    surfaceVariant = SlateLighter,
    onSurfaceVariant = TextGray,
    error = ShortCircuitRed,
    onError = TextWhite
)

@Composable
fun CircuitQueestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CircuitQueestColorScheme,
        typography = CircuitQueestTypography,
        content = content
    )
}

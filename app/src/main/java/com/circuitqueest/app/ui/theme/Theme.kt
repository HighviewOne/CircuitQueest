package com.circuitqueest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CircuitQueestColorScheme = darkColorScheme(
    primary = CqBlue,
    onPrimary = CqText,
    primaryContainer = CqBlueDeep,
    onPrimaryContainer = CqText,
    secondary = CqGold,
    onSecondary = CqBg,
    secondaryContainer = CqSurface2,
    onSecondaryContainer = CqGold,
    tertiary = CqCyan,
    onTertiary = CqBg,
    background = CqBg,
    onBackground = CqText,
    surface = CqSurface,
    onSurface = CqText,
    surfaceVariant = CqSurface2,
    onSurfaceVariant = CqTextDim,
    outline = CqBorder,
    outlineVariant = CqBorderStrong,
    error = CqRed,
    onError = CqText
)

@Composable
fun CircuitQueestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CircuitQueestColorScheme,
        typography = CircuitQueestTypography,
        content = content
    )
}

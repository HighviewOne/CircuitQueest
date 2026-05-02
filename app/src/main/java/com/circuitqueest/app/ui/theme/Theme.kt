package com.circuitqueest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val CockpitColorScheme = darkColorScheme(
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

private val BlueprintColorScheme = darkColorScheme(
    primary = CqCyan,
    onPrimary = BpBg,
    primaryContainer = BpSurface2,
    onPrimaryContainer = CqText,
    secondary = CqGold,
    onSecondary = BpBg,
    secondaryContainer = BpSurface2,
    onSecondaryContainer = CqGold,
    tertiary = CqBlueLight,
    onTertiary = BpBg,
    background = BpBg,
    onBackground = CqText,
    surface = BpSurface,
    onSurface = CqText,
    surfaceVariant = BpSurface2,
    onSurfaceVariant = CqTextDim,
    outline = BpBorder,
    outlineVariant = BpBorderStrong,
    error = CqRed,
    onError = CqText
)

@Composable
fun CircuitQueestTheme(
    blueprintMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val palette = if (blueprintMode) BlueprintPalette else CockpitPalette
    val colorScheme = if (blueprintMode) BlueprintColorScheme else CockpitColorScheme
    CompositionLocalProvider(LocalCqPalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CircuitQueestTypography,
            content = content
        )
    }
}

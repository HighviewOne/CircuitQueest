package com.circuitqueest.app.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class CqPalette(
    val bg: Color,
    val bg2: Color,
    val surface: Color,
    val surface2: Color,
    val border: Color,
    val borderStrong: Color,
    val track: Color
)

val CockpitPalette = CqPalette(
    bg = CqBg, bg2 = CqBg2,
    surface = CqSurface, surface2 = CqSurface2,
    border = CqBorder, borderStrong = CqBorderStrong,
    track = CqTrack
)

val BlueprintPalette = CqPalette(
    bg = BpBg, bg2 = BpBg2,
    surface = BpSurface, surface2 = BpSurface2,
    border = BpBorder, borderStrong = BpBorderStrong,
    track = BpTrack
)

val LocalCqPalette = compositionLocalOf { CockpitPalette }

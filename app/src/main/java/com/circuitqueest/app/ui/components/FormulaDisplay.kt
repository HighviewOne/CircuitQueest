package com.circuitqueest.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormulaDisplay(formula: String, modifier: Modifier = Modifier) {
    FormulaTile(formula = formula, modifier = modifier)
}

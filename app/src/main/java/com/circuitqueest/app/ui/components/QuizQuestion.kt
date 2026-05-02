package com.circuitqueest.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.circuitqueest.app.ui.theme.CqBlue
import com.circuitqueest.app.ui.theme.CqGold
import com.circuitqueest.app.ui.theme.CqGreen
import com.circuitqueest.app.ui.theme.CqRed
import com.circuitqueest.app.ui.theme.CqText
import com.circuitqueest.app.ui.theme.CqTextDim
import com.circuitqueest.app.ui.theme.JetBrainsMono
import com.circuitqueest.app.ui.theme.LocalCqPalette
import com.circuitqueest.app.ui.theme.MonoLabel
import com.circuitqueest.app.ui.theme.Radius
import com.circuitqueest.app.ui.theme.SpaceGrotesk
import com.circuitqueest.app.ui.theme.Spacing
import kotlinx.coroutines.launch

// ── Multiple Choice ───────────────────────────────────────────────────────────

@Composable
fun MultipleChoiceQuestion(
    questionText: String,
    options: List<String>,
    questionNumber: Int,
    correctIndex: Int,
    isSubmitted: Boolean,
    onAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pal = LocalCqPalette.current
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
        QuestionCard(questionNumber = questionNumber, questionText = questionText)

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            options.forEachIndexed { index, option ->
                OptionCard(
                    letter = ('A' + index).toString(),
                    text = option,
                    state = optionState(index, selectedIndex, correctIndex, isSubmitted),
                    isSubmitted = isSubmitted,
                    onClick = { if (!isSubmitted) selectedIndex = index }
                )
            }
        }

        if (!isSubmitted) {
            Button(
                onClick = { if (selectedIndex >= 0) onAnswer(selectedIndex) },
                enabled = selectedIndex >= 0,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CqBlue,
                    contentColor = CqText,
                    disabledContainerColor = pal.border,
                    disabledContentColor = CqTextDim
                )
            ) {
                Text(
                    text = "Submit Answer",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

private enum class OptionState { DEFAULT, SELECTED, CORRECT, WRONG, CORRECT_UNSELECTED }

private fun optionState(
    index: Int,
    selectedIndex: Int,
    correctIndex: Int,
    isSubmitted: Boolean
): OptionState = when {
    !isSubmitted && index == selectedIndex -> OptionState.SELECTED
    !isSubmitted -> OptionState.DEFAULT
    index == correctIndex -> OptionState.CORRECT
    index == selectedIndex -> OptionState.WRONG
    else -> OptionState.CORRECT_UNSELECTED
}

@Composable
private fun OptionCard(
    letter: String,
    text: String,
    state: OptionState,
    isSubmitted: Boolean,
    onClick: () -> Unit
) {
    val pal = LocalCqPalette.current
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isSubmitted) {
        if (isSubmitted && (state == OptionState.CORRECT || state == OptionState.WRONG)) {
            scope.launch {
                scale.animateTo(1.02f, tween(100))
                scale.animateTo(1f, tween(100))
            }
        }
    }

    val (cardBg, cardBorder, chipBg, chipBorder, chipTextColor) = when (state) {
        OptionState.SELECTED -> OptionColors(
            CqBlue.copy(alpha = 0.08f), CqBlue,
            CqBlue.copy(alpha = 0.15f), CqBlue, CqBlue
        )
        OptionState.CORRECT -> OptionColors(
            CqGreen.copy(alpha = 0.08f), CqGreen,
            CqGreen.copy(alpha = 0.15f), CqGreen, CqGreen
        )
        OptionState.WRONG -> OptionColors(
            CqRed.copy(alpha = 0.08f), CqRed,
            CqRed.copy(alpha = 0.15f), CqRed, CqRed
        )
        OptionState.CORRECT_UNSELECTED -> OptionColors(
            pal.surface, pal.border.copy(alpha = 0.4f),
            pal.bg2, pal.border.copy(alpha = 0.4f), CqTextDim.copy(alpha = 0.4f)
        )
        OptionState.DEFAULT -> OptionColors(pal.surface, pal.border, pal.bg2, pal.border, CqTextDim)
    }

    val shape = RoundedCornerShape(Radius.md)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale.value; scaleY = scale.value }
            .clip(shape)
            .background(cardBg)
            .border(1.dp, cardBorder, shape)
            .clickable(enabled = !isSubmitted, onClick = onClick)
            .padding(horizontal = Spacing.s12, vertical = Spacing.s12),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(Radius.sm))
                .background(chipBg)
                .border(1.dp, chipBorder, RoundedCornerShape(Radius.sm)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = chipTextColor
            )
        }
        Text(
            text = text,
            fontFamily = SpaceGrotesk,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = if (state == OptionState.CORRECT_UNSELECTED) CqTextDim.copy(alpha = 0.5f)
            else CqText,
            modifier = Modifier.weight(1f)
        )
    }
}

private data class OptionColors(
    val cardBg: Color,
    val cardBorder: Color,
    val chipBg: Color,
    val chipBorder: Color,
    val chipTextColor: Color
)

// ── Numeric Input ─────────────────────────────────────────────────────────────

@Composable
fun NumericInputQuestion(
    questionText: String,
    unit: String,
    questionNumber: Int,
    isSubmitted: Boolean,
    onAnswer: (Double?) -> Unit,
    modifier: Modifier = Modifier
) {
    val pal = LocalCqPalette.current
    var inputText by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
        QuestionCard(questionNumber = questionNumber, questionText = questionText)

        val fieldShape = RoundedCornerShape(Radius.md)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(fieldShape)
                .background(pal.surface)
                .border(
                    1.dp,
                    if (isSubmitted) pal.border.copy(alpha = 0.4f) else pal.border,
                    fieldShape
                )
                .padding(horizontal = Spacing.s16),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.s12)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (inputText.isEmpty() && !isSubmitted) {
                        Text("0.00", fontFamily = JetBrainsMono, fontSize = 32.sp,
                            color = CqTextDim, textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    }
                    BasicTextField(
                        value = inputText,
                        onValueChange = { if (!isSubmitted) inputText = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 32.sp,
                            color = CqText,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (inputText.isNotBlank() && !isSubmitted)
                                onAnswer(inputText.toDoubleOrNull())
                        }),
                        singleLine = true,
                        cursorBrush = SolidColor(CqText),
                        enabled = !isSubmitted,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        style = MonoLabel.copy(fontSize = 14.sp),
                        color = CqGold
                    )
                }
            }
        }

        if (!isSubmitted) {
            Button(
                onClick = { if (inputText.isNotBlank()) onAnswer(inputText.toDoubleOrNull()) },
                enabled = inputText.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CqBlue,
                    contentColor = CqText,
                    disabledContainerColor = pal.border,
                    disabledContentColor = CqTextDim
                )
            ) {
                Text("Submit Answer", fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }
        }
    }
}

// ── Shared: question card ─────────────────────────────────────────────────────

@Composable
fun QuestionCard(questionNumber: Int, questionText: String, modifier: Modifier = Modifier) {
    val pal = LocalCqPalette.current
    val shape = RoundedCornerShape(Radius.xl)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(pal.surface)
            .border(1.dp, pal.border, shape)
            .padding(Spacing.s24)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s12)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Radius.sm))
                    .background(pal.bg)
                    .border(1.dp, pal.border, RoundedCornerShape(Radius.sm))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(text = "Q$questionNumber", style = MonoLabel, color = CqTextDim)
            }
            Text(
                text = questionText,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                color = CqText
            )
        }
    }
}

// ── Answer feedback ───────────────────────────────────────────────────────────

@Composable
fun AnswerFeedback(
    isCorrect: Boolean,
    explanation: String,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pal = LocalCqPalette.current
    val color = if (isCorrect) CqGreen else CqRed
    val shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color.copy(alpha = 0.08f))
            .border(1.dp, color.copy(alpha = 0.4f), shape)
            .padding(Spacing.s24)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.s8)) {
            Text(
                text = if (isCorrect) "Circuit closed!" else "Open circuit",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = color
            )
            Text(text = explanation, fontFamily = SpaceGrotesk, fontSize = 14.sp,
                lineHeight = 20.sp, color = CqText)
            Spacer(modifier = Modifier.height(Spacing.s4))
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(Radius.md),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color, contentColor = pal.bg
                )
            ) {
                Text("Next  →", fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }
        }
    }
}

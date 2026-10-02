package com.circuitqueest.app.ui.components

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NumericAnswerParsingTest {

    @Test
    fun parsesDotDecimal() {
        assertEquals(0.5, parseNumericAnswer("0.5"))
    }

    @Test
    fun parsesCommaDecimal() {
        assertEquals(0.5, parseNumericAnswer("0,5"))
    }

    @Test
    fun trimsWhitespace() {
        assertEquals(12.0, parseNumericAnswer(" 12 "))
    }

    @Test
    fun rejectsGarbage() {
        assertNull(parseNumericAnswer("abc"))
    }
}

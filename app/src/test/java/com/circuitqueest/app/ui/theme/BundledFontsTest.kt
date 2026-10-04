package com.circuitqueest.app.ui.theme

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

/**
 * Guards against committing something that isn't a font under res/font (the Space
 * Grotesk files were once saved HTML pages, which crashed every build on launch).
 */
class BundledFontsTest {

    // Unit tests run with the module directory as the working directory.
    private val fontDir = File("src/main/res/font")

    @Test
    fun everyBundledFont_hasATrueTypeOrOpenTypeSignature() {
        val fonts = fontDir.listFiles { f -> f.extension in setOf("ttf", "otf") }.orEmpty()
        assertTrue(fonts.size >= 7, "expected bundled fonts in ${fontDir.absolutePath}")

        val validSignatures = setOf(
            listOf(0x00, 0x01, 0x00, 0x00), // TrueType
            "true".map { it.code },
            "OTTO".map { it.code } // CFF OpenType
        )
        fonts.forEach { font ->
            val header = font.inputStream().use { input -> List(4) { input.read() } }
            assertTrue(header in validSignatures, "${font.name} is not a font file (header $header)")
        }
    }
}

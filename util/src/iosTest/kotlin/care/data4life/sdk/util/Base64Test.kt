/*
 * Copyright (c) 2021 D4L data4life gGmbH / All rights reserved.
 *
 * D4L owns all legal rights, title and interest in and to the Software Development Kit ("SDK"),
 * including any intellectual property rights that subsist in the SDK.
 *
 * The SDK and its documentation may be accessed and used for viewing/review purposes only.
 * Any usage of the SDK for other purposes, including usage for the development of
 * applications/third-party applications shall require the conclusion of a license agreement
 * between you and D4L.
 *
 * If you are interested in licensing the SDK for your own applications/third-party
 * applications and/or if you’d like to contribute to the development of the SDK, please
 * contact D4L by email to help@data4life.care.
 */

package care.data4life.sdk.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Base64Test {
    private fun String.asciiToByteArray() = ByteArray(length) {
        get(it).toByte()
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encode is called with a string it encodes it`() {
        assertTrue(
            Base64.encode("Kotlin is awesome".asciiToByteArray()).contentEquals(
                "S290bGluIGlzIGF3ZXNvbWU=".asciiToByteArray()
            )
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encode is called with a string it encodes it pads the resulting ByteArray correctly`() {
        val inputOutputPairs = mapOf(
            "" to "",
            "1" to "MQ==",
            "22" to "MjI=",
            "333" to "MzMz",
            "4444" to "NDQ0NA=="
        )

        inputOutputPairs.forEach { (input, expectedOutput) ->
            assertTrue(
                Base64.encode(input.asciiToByteArray()).contentEquals(
                    expectedOutput.asciiToByteArray()
                )
            )
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encodeToString is called with a ByteArray it encodes it`() {
        assertEquals(
            actual = Base64.encodeToString("Kotlin is awesome".asciiToByteArray()),
            expected = "S290bGluIGlzIGF3ZXNvbWU="
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encodeToString is called with a ByteArray it pads the resulting String correctly`() {
        val inputOutputPairs = mapOf(
            "" to "",
            "1" to "MQ==",
            "22" to "MjI=",
            "333" to "MzMz",
            "4444" to "NDQ0NA=="
        )

        inputOutputPairs.forEach { (input, expectedOutput) ->
            assertEquals(
                actual = Base64.encodeToString(input.asciiToByteArray()),
                expected = expectedOutput
            )
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encodeToString is called with a String it encodes it`() {
        assertEquals(
            actual = Base64.encodeToString("Kotlin is awesome"),
            expected = "S290bGluIGlzIGF3ZXNvbWU="
        )
    }

    @ExperimentalStdlibApi
    @Test
    fun `Given encodeToString is called with a String it pads the resulting String correctly`() {
        val inputOutputPairs = mapOf(
            "" to "",
            "1" to "MQ==",
            "22" to "MjI=",
            "333" to "MzMz",
            "4444" to "NDQ0NA=="
        )

        inputOutputPairs.forEach { (input, expectedOutput) ->
            assertEquals(
                actual = Base64.encodeToString(input),
                expected = expectedOutput
            )
        }
    }
}

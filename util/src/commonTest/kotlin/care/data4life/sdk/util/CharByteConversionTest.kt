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


class CharByteConversionTest {
    private val testCharArray = charArrayOf('a', 'b', 'c')
    private val testByteArray = byteArrayOf('a'.toByte(), 'b'.toByte(), 'c'.toByte())

    @Test
    fun toBytesTest() {
        assertEquals(testByteArray.size, testCharArray.toBytes().size)
        testCharArray.toBytes().forEachIndexed { index, byte ->
            assertEquals(testByteArray[index], byte)
        }
    }

    @Test
    fun toCharArrayTest() {
        assertEquals(testCharArray.size, testByteArray.toChars().size)
        testByteArray.toChars().forEachIndexed { index, char ->
            assertEquals(testCharArray[index], char)
        }
    }

    @Test
    fun toByteAndBackTest() {
        val toCharsFinal = testCharArray.toBytes().toChars()
        assertEquals(testCharArray.size, toCharsFinal.size)
        toCharsFinal.forEachIndexed { index, char ->
            assertEquals(testCharArray[index], char)
        }
    }

    @Test
    fun toCharsAndBackTest() {
        val toByteFinal = testByteArray.toChars().toBytes()
        assertEquals(testByteArray.size, toByteFinal.size)
        toByteFinal.forEachIndexed { index, bytes ->
            assertEquals(testByteArray[index], bytes)
        }
    }
}

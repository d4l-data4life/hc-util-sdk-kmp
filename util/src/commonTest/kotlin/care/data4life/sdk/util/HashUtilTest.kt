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
import kotlin.test.assertFailsWith

class HashUtilTest {

    @Test
    fun sha1_NoData_ShouldThrowException() {
        // when
        assertFailsWith<IllegalStateException> {
            HashUtil.sha1(null)
        }
    }

    @Test
    fun sha1_emptyData_ShouldThrowException() {
        // when
        assertFailsWith<IllegalStateException> {
            HashUtil.sha1(ByteArray(0))
        }
    }

    @Test
    fun sha1() {
        // given
        val data = "The quick brown fox jumps over the lazy dog"

        // when
        val result = HashUtil.sha1(data.encodeToByteArray())

        // then
        assertEquals(
            expected = "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12",
            actual = HexFormatter.format(result),
            "Failed to create sha1"
        )
    }

    @Test
    fun sha1String_NoData_ShouldThrowException() {
        // when
        assertFailsWith<IllegalStateException> {
            HashUtil.sha1String(null)
        }
    }

    @Test
    fun sha1String_emptyData_ShouldThrowException() {
        // when
        assertFailsWith<IllegalStateException> {
            HashUtil.sha1String(ByteArray(0))
        }
    }

    @Test
    fun sha1String() {
        // given
        val data = "The quick brown fox jumps over the lazy dog"

        // when
        val result = HashUtil.sha1String(data.encodeToByteArray())

        // then
        assertEquals(
            "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12",
            result,
            "Failed to create sha1"
        )
    }
}

expect object HexFormatter {
    fun format(toFormat: ByteArray): String
}

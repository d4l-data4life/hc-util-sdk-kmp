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

import kotlin.test.assertEquals
import org.junit.Test

class HashUtilTest {

    @Test(expected = IllegalStateException::class)
    fun sha1_NoData_ShouldThrowException() {
        // when
        HashUtil.sha1(null)
    }

    @Test
    fun sha1() {
        // given
        val data = "The quick brown fox jumps over the lazy dog"

        // when
        val result = HashUtil.sha1(data.toByteArray())

        // then
        val hexResult = StringBuilder(result.size * 2)
        for (byte in result)
            hexResult.append("%02x".format(byte))
        assertEquals(
            "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12",
            hexResult.toString(),
            "Failed to create sha1"
        )
    }

    @Test(expected = IllegalStateException::class)
    fun sha1String_NoData_ShouldThrowException() {
        // when
        HashUtil.sha1String(null)
    }

    @Test
    fun sha1String() {
        // given
        val data = "The quick brown fox jumps over the lazy dog"

        // when
        val result = HashUtil.sha1String(data.toByteArray())

        // then
        assertEquals(
            "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12",
            result,
            "Failed to create sha1"
        )
    }
}

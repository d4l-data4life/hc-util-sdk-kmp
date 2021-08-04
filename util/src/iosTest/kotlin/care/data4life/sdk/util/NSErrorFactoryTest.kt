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

import co.touchlab.stately.isFrozen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class NSErrorFactoryTest {
    @Test
    fun `Given create is called with a Code, Domain and LocalizedDescription, it returns a NSError`() {
        // Given
        val code: Long = 23
        val domain = "potato"
        val localized = "soup"
        val kotlinError = RuntimeException()

        // When
        val error = NSErrorFactory.create(code, domain, localized, kotlinError)

        // Then
        assertEquals(
            actual = error.code,
            expected = code
        )
        assertEquals(
            actual = error.domain,
            expected = domain
        )
        assertEquals(
            actual = error.localizedDescription,
            expected = localized
        )
        assertSame(
            actual = error.userInfo["kotlinError"],
            expected = kotlinError
        )
        assertTrue((error.userInfo["kotlinError"] as Throwable).isFrozen)
    }
}

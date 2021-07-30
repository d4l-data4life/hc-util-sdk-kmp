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

package care.data4life.sdk.util.coroutine

import co.touchlab.stately.isFrozen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class D4lSDKFlowIosTest {
    @Test
    fun `Given a Flow had been initialized it is frozen`() {
        // Given
        val flow = D4LSDKFlow(GlobalScope, flow<Unit> { })

        // Then
        assertTrue(flow.isFrozen)
    }

    @Test
    fun `Its KtFlow  is frozen`() {
        // Given
        val flow = D4LSDKFlow(GlobalScope, flow<Unit> { })

        // Then
        assertTrue(flow.ktFlow.isFrozen)
    }

    @Test
    fun `Given subscribe is called it returns a Job which is frozen`() {
        // Given
        val ktFlow = flow<Unit> { }

        // When
        val job = D4LSDKFlow(GlobalScope, ktFlow).subscribe(
            {},
            {},
            {}
        )

        // Then
        assertTrue(job.isFrozen)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Given subscribe is called with a scope it launches it in the given scope`() {
        // Given
        val context: CoroutineContext = newSingleThreadContext("testRunner2")
        val scope = CoroutineScope(context)
        val ktFlow = flow<Unit> {}

        // When
        val job = D4LSDKFlow(scope, ktFlow).subscribe(
            {},
            {},
            {}
        )
        // Then
        assertTrue(job.isActive)
        scope.cancel()
        assertFalse(job.isActive)
    }
}

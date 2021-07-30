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

import care.data4life.sdk.util.test.coroutine.runBlockingTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue

class D4LSDKFlowTest {
    @Test
    fun `It exposes its wrapped Flow`() {
        // Given
        val ktFlow = flow<Unit> { }

        // When
        val result = D4LSDKFlow(
            GlobalScope,
            ktFlow
        ).ktFlow

        // Then
        assertSame(
            actual = result,
            expected = result
        )
    }

    @Test
    fun `Given subscribe is called it returns a Job`() {
        // Given
        val ktFlow = flow<Unit> { }

        // When
        val job: Any = D4LSDKFlow(
            GlobalScope,
            ktFlow
        ).subscribe(
            {},
            {},
            {}
        )

        // Then
        assertTrue(job is Job)
    }

    @Test
    fun `Given subscribe is called with a Closure to receive emitted Items it delegates the emitted Item to there`() {
        // Given
        val item = object {}
        val ktFlow = flow<Any> {
            emit(item)
        }

        val capturedItem = Channel<Any>()

        // When
        val job = D4LSDKFlow(
            GlobalScope,
            ktFlow
        ).subscribe(
            { delegatedItem ->
                GlobalScope.launch {
                    capturedItem.send(delegatedItem)
                }
            },
            {},
            {}
        )

        // Then
        runBlockingTest {
            job.join()

            assertSame(
                actual = capturedItem.receive(),
                expected = item
            )
        }
    }

    @Test
    fun `Given subscribe is called with a Closure to receive emitted Errors it delegates the emitted Errors to there`() {
        // Given
        val error = RuntimeException()
        val ktFlow = flow<Any> {
            throw error
        }

        val capturedError = Channel<Throwable>()

        // When
        val job = D4LSDKFlow(
            GlobalScope,
            ktFlow
        ).subscribe(
            {},
            onError = { delegatedError ->
                GlobalScope.launch {
                    capturedError.send(delegatedError)
                }
            }
        )

        // Then
        runBlockingTest {
            job.join()

            assertSame(
                actual = capturedError.receive(),
                expected = error
            )
        }
    }

    @Test
    fun `Given subscribe is called with a Closure be notified if the Flow had been completed, it calls the Closure`() {
        // Given
        val wasCalled = Channel<Boolean>()
        val ktFlow = flow<Unit> {}

        // When
        val job = D4LSDKFlow(
            GlobalScope,
            ktFlow
        ).subscribe(
            {},
            {},
            onComplete = {
                GlobalScope.launch {
                    wasCalled.send(true)
                }
            }
        )

        // Then
        runBlockingTest {
            job.join()

            assertTrue(wasCalled.receive())
        }
    }
}

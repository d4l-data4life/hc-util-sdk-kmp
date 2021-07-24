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

package care.data4life.sdk.util.test.ktor

import care.data4life.sdk.util.test.coroutine.runBlockingTest
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class KtorMockClientHelperTest {
    @Test
    fun `Given createHelloWorldOkResponse is called, it creates OkResponse, which contains Hello World`() = runBlockingTest {
        // Given
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    createHelloWorldOkResponse(this)
                }
            }
        }

        // When
        val responsePayload = client.get<String>("potato")
        val response = (client.engine as MockEngine).responseHistory[0]

        // Then
        assertEquals(
            actual = responsePayload,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response.statusCode,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response.headers,
            expected = headersOf(
                "Content-Type" to listOf(
                    ContentType.Text.Plain.toString()
                )
            )
        )
    }

    @Test
    fun `Given createHelloWorldMockClient is called it creates a MockClient which sends always a OkResponse`() = runBlockingTest {
        // Given
        val client = createHelloWorldMockClient()

        // When
        val response1: HttpResponse = client.get("does not matter")
        val response2: HttpResponse = client.post("does not matter")
        val response3: HttpResponse = client.put("does not matter")
        val response4: HttpResponse = client.delete("does not matter")
        val response5: HttpResponse = client.patch("does not matter")
        val response6: HttpResponse = client.head("does not matter")

        // Then
        assertEquals(
            actual = response1.status,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response2.status,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response3.status,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response4.status,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response5.status,
            expected = HttpStatusCode.OK
        )
        assertEquals(
            actual = response6.status,
            expected = HttpStatusCode.OK
        )
    }

    @Test
    fun `Given createHelloWorldMockClient is it creates a MockClient which responds always Hello World`() = runBlockingTest {
        // Given
        val client = createHelloWorldMockClient()

        // When
        val response1: String = client.get("does not matter")
        val response2: String = client.post("does not matter")
        val response3: String = client.put("does not matter")
        val response4: String = client.head("does not matter")
        val response5: String = client.patch("does not matter")
        val response6: String = client.delete("does not matter")

        // Then
        assertEquals(
            actual = response1,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response2,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response3,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response4,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response5,
            expected = "Hello World!"
        )
        assertEquals(
            actual = response6,
            expected = "Hello World!"
        )
    }

    @Test
    fun `Given createErrorMockClient is called with a Error it creates a MockClient which propagates always the given Error`() = runBlockingTest {
        // Given
        val error = RuntimeException()
        val client = createErrorMockClient(error)

        // When
        val response1 = assertFailsWith<RuntimeException> {
            client.get<Any>("does not matter")
        }
        val response2 = assertFailsWith<RuntimeException> {
            client.post("does not matter")
        }
        val response3 = assertFailsWith<RuntimeException> {
            client.put<Any>("does not matter")
        }
        val response4 = assertFailsWith<RuntimeException> {
            client.head<Any>("does not matter")
        }
        val response5 = assertFailsWith<RuntimeException> {
            client.patch<Any>("does not matter")
        }
        val response6 = assertFailsWith<RuntimeException> {
            client.delete<Any>("does not matter")
        }

        // Then
        assertSame(
            actual = response1,
            expected = error
        )
        assertSame(
            actual = response2,
            expected = error
        )
        assertSame(
            actual = response3,
            expected = error
        )
        assertSame(
            actual = response4,
            expected = error
        )
        assertSame(
            actual = response5,
            expected = error
        )
        assertSame(
            actual = response6,
            expected = error
        )
    }

    @Test
    fun `Given createMockClientWithResponse is called with Closure which builds HttpResponseData it creates a MockClient which utilises the given HttpResponseData`() = runBlockingTest {
        // Given
        val client = createMockClientWithResponse { scope ->
            scope.respond(
                content = "Not Hello World!"
            )
        }

        // When
        val response1: String = client.get("does not matter")
        val response2: String = client.post("does not matter")
        val response3: String = client.put("does not matter")
        val response4: String = client.head("does not matter")
        val response5: String = client.patch("does not matter")
        val response6: String = client.delete("does not matter")

        // Then
        assertEquals(
            actual = response1,
            expected = "Not Hello World!"
        )
        assertEquals(
            actual = response2,
            expected = "Not Hello World!"
        )
        assertEquals(
            actual = response3,
            expected = "Not Hello World!"
        )
        assertEquals(
            actual = response4,
            expected = "Not Hello World!"
        )
        assertEquals(
            actual = response5,
            expected = "Not Hello World!"
        )
        assertEquals(
            actual = response6,
            expected = "Not Hello World!"
        )
    }

    @Test
    // see: HttpMockObjectResponse
    fun `Given createMockClientWithResponse is called with List of HttpResponseObjects and a Closure it creates a MockClient which utilises the given HttpResponseObjects`() = runBlockingTest {
        // Given
        val referenceObject = emptyList<Any>()
        val objects = listOf(
            referenceObject
        )
        val client = createMockClientWithResponse(objects) { scope ->
            scope.respond(
                content = "Not Hello World!"
            )
        }

        // When
        val response1: List<Any> = client.get("does not matter")
        val response2: List<Any> = client.post("does not matter")
        val response3: List<Any> = client.put("does not matter")
        val response4: List<Any> = client.head("does not matter")
        val response5: List<Any> = client.patch("does not matter")
        val response6: List<Any> = client.delete("does not matter")

        // Then
        assertSame(
            actual = response1,
            expected = referenceObject
        )
        assertSame(
            actual = response2,
            expected = referenceObject
        )
        assertSame(
            actual = response3,
            expected = referenceObject
        )
        assertSame(
            actual = response4,
            expected = referenceObject
        )
        assertSame(
            actual = response5,
            expected = referenceObject
        )
        assertSame(
            actual = response6,
            expected = referenceObject
        )
    }
}

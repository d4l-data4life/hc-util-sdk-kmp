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

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

fun createHelloWorldOkResponse(scope: MockRequestHandleScope): HttpResponseData {
    return scope.respond(
        "Hello World!",
        headers = headersOf(
            "Content-Type" to listOf(
                ContentType.Text.Plain.toString()
            )
        )
    )
}

fun createHelloWorldMockClient(): HttpClient {
    return HttpClient(MockEngine) {
        engine {
            addHandler {
                createHelloWorldOkResponse(this)
            }
        }
    }
}

fun createErrorMockClient(
    error: Throwable
): HttpClient {
    return HttpClient(MockEngine) {
        engine {
            addHandler {
                respondError(
                    status = HttpStatusCode.InternalServerError
                )
            }
        }

        HttpResponseValidator {
            handleResponseException {
                throw error
            }
        }
    }
}

fun createMockClientWithResponse(
    httpResponseObjects: List<Any>? = null,
    httpResponse: (scope: MockRequestHandleScope, HttpRequestData) -> HttpResponseData,
): HttpClient {
    return HttpClient(MockEngine) {
        if (httpResponseObjects != null) {
            install(HttpMockObjectResponse) {
                addResponses(httpResponseObjects)
            }
        }

        engine {
            addHandler {
                httpResponse(this, it)
            }
        }
    }
}

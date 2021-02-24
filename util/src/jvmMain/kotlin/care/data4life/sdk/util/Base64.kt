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

actual object Base64 {

    private const val CHARSET_UTF8 = "UTF-8"
    private val CHARSET = charset(CHARSET_UTF8)

    actual fun encode(data: ByteArray): ByteArray {
        return java.util.Base64.getEncoder().encode(data)
    }

    actual fun encodeToString(data: ByteArray): String {
        return java.util.Base64.getEncoder().encodeToString(data)
    }

    actual fun encodeToString(data: String): String {
        return java.util.Base64.getEncoder().encodeToString(data.toByteArray(CHARSET))
    }

    actual fun decode(encodedData: ByteArray): ByteArray {
        return java.util.Base64.getDecoder().decode(encodedData)
    }

    actual fun decode(encodedData: String): ByteArray {
        return java.util.Base64.getDecoder().decode(encodedData)
    }

    actual fun decodeToString(encodedData: String): String {
        return java.util.Base64.getDecoder().decode(encodedData).toString(CHARSET)
    }
}

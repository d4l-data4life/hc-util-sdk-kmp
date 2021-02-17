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
    private const val DEFAULT_FLAG = android.util.Base64.NO_WRAP
    private val CHARSET = charset(CHARSET_UTF8)

    actual fun encode(data: ByteArray): ByteArray {
        return android.util.Base64.encode(data, DEFAULT_FLAG)
    }

    actual fun encodeToString(data: ByteArray): String {
        return android.util.Base64.encodeToString(data, DEFAULT_FLAG)
    }

    actual fun encodeToString(data: String): String {
        return android.util.Base64.encodeToString(data.toByteArray(CHARSET), DEFAULT_FLAG)
    }

    actual fun decode(encodedData: ByteArray): ByteArray {
        return android.util.Base64.decode(encodedData, DEFAULT_FLAG)
    }

    actual fun decode(encodedData: String): ByteArray {
        return android.util.Base64.decode(encodedData.toByteArray(CHARSET), DEFAULT_FLAG)
    }

    actual fun decodeToString(encodedData: String): String {
        return String(
            android.util.Base64.decode(encodedData.toByteArray(CHARSET), DEFAULT_FLAG),
            CHARSET
        )
    }
}

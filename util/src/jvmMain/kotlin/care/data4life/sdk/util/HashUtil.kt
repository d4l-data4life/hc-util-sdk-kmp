/*
 * Copyright (c) 2020 D4L data4life gGmbH / All rights reserved.
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

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

actual object HashUtil {

    actual fun sha1(data: ByteArray?): ByteArray {
        check(data != null && data.isNotEmpty()) { DATA_IS_REQUIRED }

        try {
            return MessageDigest.getInstance(SHA_ALGORITHM).digest(data)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(NO_ALGORITHM_ERROR, e)
        }
    }

    actual fun sha1String(data: ByteArray?): String {
        val result = sha1(data)

        val hexResult = StringBuilder(result.size * 2)
        for (byte in result) {
            hexResult.append(HEX_FORMAT.format(byte))
        }
        return hexResult.toString()
    }
}

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

import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create

actual object Base64 {
    private const val OUT_OF_MEMORY = "Out of memory."

    @ExperimentalUnsignedTypes
    actual fun encode(data: ByteArray): ByteArray {
        return encodeToString(data).encodeToByteArray()
    }

    @ExperimentalUnsignedTypes
    actual fun encodeToString(data: ByteArray): String {
        val nsData = NSDataMapper.toNSData(data)
        return nsData.base64EncodedStringWithOptions(0)
    }

    @ExperimentalUnsignedTypes
    actual fun encodeToString(data: String): String {
        return encodeToString(data.encodeToByteArray())
    }

    @Throws(NullPointerException::class)
    private fun platformDecode(encodedData: String): NSData {
        return memScoped {
            NSData.create(
                base64EncodedString = encodedData,
                options = 0
            ) ?: throw NullPointerException(OUT_OF_MEMORY)
        }
    }

    @ExperimentalUnsignedTypes
    @Throws(NullPointerException::class)
    actual fun decode(encodedData: ByteArray): ByteArray {
        val data = memScoped {
            NSString.create(
                NSDataMapper.toNSData(encodedData),
                NSUTF8StringEncoding
            ) ?: throw NullPointerException(OUT_OF_MEMORY)
        }

        return decode(data as String)
    }

    @Throws(NullPointerException::class)
    actual fun decode(encodedData: String): ByteArray {
        return NSDataMapper.toByteArray(platformDecode(encodedData))
    }

    @Throws(NullPointerException::class)
    actual fun decodeToString(encodedData: String): String {
        return memScoped {
            NSString.create(
                platformDecode(encodedData),
                NSUTF8StringEncoding
            ) ?: throw NullPointerException(OUT_OF_MEMORY)
        } as String
    }
}

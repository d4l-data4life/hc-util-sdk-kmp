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

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.posix.memcpy

actual object Base64 {
    // see: https://github.com/JetBrains/kotlin-native/issues/3172
    // see: https://stackoverflow.com/questions/58521108/how-to-convert-kotlin-bytearray-to-nsdata-and-viceversa
    // see: https://gist.github.com/noahsark769/61cfb7a8b7231e2069a9dab94cf74a62
    private fun NSData.toByteArray(): ByteArray {
        return ByteArray(this@toByteArray.length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
            }
        }
    }

    private fun ByteArray.toNSData(): NSData {
        return memScoped {
            NSData.create(
                bytes = allocArrayOf(this@toNSData),
                length = this@toNSData.size.toULong()
            )
        }
    }

    actual fun encode(data: ByteArray): ByteArray {
        return encodeToString(data).encodeToByteArray()
    }

    actual fun encodeToString(data: ByteArray): String {
        val nsData = data.toNSData()
        return nsData.base64EncodedStringWithOptions(0)
    }

    actual fun encodeToString(data: String): String {
        return encodeToString(data.encodeToByteArray())
    }

    private fun platformDecode(encodedData: String): NSData {
        return NSData.create(
            base64EncodedString = encodedData,
            options = 0
        )!!
    }

    actual fun decode(encodedData: ByteArray): ByteArray {
        val data = NSString.create(
            encodedData.toNSData(),
            NSUTF8StringEncoding
        ) as String

        return decode(data)
    }

    actual fun decode(encodedData: String): ByteArray {
        return platformDecode(encodedData).toByteArray()
    }

    actual fun decodeToString(encodedData: String): String {
        return NSString.create(
            platformDecode(encodedData),
            NSUTF8StringEncoding
        ) as String
    }
}

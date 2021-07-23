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

import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readBytes
import platform.CoreCrypto.CC_SHA1
import platform.CoreCrypto.CC_SHA1_DIGEST_LENGTH
import platform.Foundation.NSString
import platform.Foundation.stringByAppendingFormat
import platform.posix.uint8_tVar

actual object HashUtil {
    private const val HEX_FORMAT = "%02hhx" // see: https://stackoverflow.com/questions/39075043/how-to-convert-data-to-hex-string-in-swift
    private const val OUT_OF_MEM = "Cannot perform sha1."

    @ExperimentalUnsignedTypes
    actual fun sha1(data: ByteArray?): ByteArray {
        check(data is ByteArray && data.isNotEmpty()) { DATA_IS_REQUIRED }

        val nsData = NSDataMapper.toNSData(data)
        return memScoped {
            val digest = allocArray<uint8_tVar>(CC_SHA1_DIGEST_LENGTH)

            CC_SHA1(
                nsData.bytes,
                nsData.length.toUInt(),
                digest
            ) ?: throw RuntimeException(OUT_OF_MEM)

            return@memScoped digest.readBytes(CC_SHA1_DIGEST_LENGTH)
        }
    }

    @ExperimentalUnsignedTypes
    @Throws(RuntimeException::class)
    actual fun sha1String(data: ByteArray?): String {
        val bytes = sha1(data)
        val sha1String = StringBuilder(bytes.size * 2)
        for (byte in bytes) {
            sha1String.append(("" as NSString).stringByAppendingFormat(HEX_FORMAT, byte))
        }

        return sha1String.toString()
    }
}

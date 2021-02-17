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

enum class MimeType(val contentType: String) {
    JPEG("image/jpg"),
    PNG("image/png"),
    TIFF("image/tiff"),
    PDF("application/pdf"),
    DCM("application/dicom"),
    UNKNOWN("");


    //source: https://en.wikipedia.org/wiki/List_of_file_signatures
    fun byteSignature(): Array<Array<Byte?>?> {
        when (this) {
            JPEG -> return arrayOf(
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xDB.toByte()),
                // JPEG, JFIF, JPG, JPE
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE0.toByte()),
                // Digital camera JPG using Exchangeable Image File Format (EXIF)
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE1.toByte()),
                // CANNON EOS JPEG FILE
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE2.toByte()),
                // SAMSUNG D500 JPEG FILE
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE3.toByte()),
                // Still Picture Interchange File Format (SPIFF)
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE8.toByte()),
                arrayOf<Byte?>(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xEE.toByte())
            )
            PNG -> return arrayOf(
                arrayOf<Byte?>(
                    0x89.toByte(),
                    0x50,
                    0x4E,
                    0x47,
                    0x0D,
                    0x0A,
                    0x1A,
                    0x0A
                )
            )
            TIFF -> return arrayOf(
                arrayOf<Byte?>(0x4D, 0x4D, 0x00, 0x2A),
                arrayOf<Byte?>(0x49, 0x49, 0x2A, 0x00)
            )
            PDF -> return arrayOf(arrayOf<Byte?>(0x25, 0x50, 0x44, 0x46, 0x2d))
            DCM -> return arrayOf(arrayOf<Byte?>(0x44, 0x49, 0x43, 0x4D))
            else -> return arrayOfNulls(0)
        }
    }

    fun offset(): Int = when (this) {
        DCM -> 0x80
        else -> 0
    }


    companion object {

        fun recognizeMimeType(data: ByteArray?): MimeType {
            if (data == null || data.isEmpty()) return UNKNOWN

            for (mimeType in values()) {
                for (mimeSignature in mimeType.byteSignature()) {
                    val upperBound = mimeType.offset() + (mimeSignature?.size ?: 0)
                    if (data.size < upperBound) continue


                    val magicBytes = data.copyOfRange(mimeType.offset(), upperBound)
                    if (areArraysEqual(magicBytes, mimeSignature)) return mimeType
                }
            }
            return UNKNOWN
        }


        private fun areArraysEqual(input: ByteArray, reference: Array<Byte?>?): Boolean {
            if (input.size != reference?.size) return false

            for (i in reference.indices) {
                if (reference[i] == null)
                    continue
                else if (input[i] != reference[i]) return false
            }
            return true
        }


    }

}

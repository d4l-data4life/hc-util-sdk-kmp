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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MimeTypeTest {

    @Test
    fun genericJpg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xDB.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun defaultJpg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE0.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun digitalCameraJpgExif() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE1.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun canonJpg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE2.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun samsungJpg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE3.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun spiffJpg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE8.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun defaultJpeg() {
        val data = byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte(), 0xE8.toByte())
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.JPEG, result)
        assertEquals(
            expected = IMAGE_JPG,
            actual = result.contentType
        )
    }

    @Test
    fun png() {
        val data = byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.PNG, result)
        assertEquals(
            expected = IMAGE_PNG,
            actual = result.contentType
        )
    }

    @Test
    fun tiff() {
        val data = byteArrayOf(0x4D, 0x4D, 0x00, 0x2A)
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.TIFF, result)
        assertEquals(
            expected = IMAGE_TIFF,
            actual = result.contentType
        )

        val data2 = byteArrayOf(0x49, 0x49, 0x2A, 0x00)
        val result2 = MimeType.recognizeMimeType(data2)

        assertEquals(MimeType.TIFF, result2)
        assertEquals(
            expected = IMAGE_TIFF,
            actual = result.contentType
        )
    }

    @Test
    fun pdf() {
        val data = byteArrayOf(0x25, 0x50, 0x44, 0x46, 0x2d)
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.PDF, result)
        assertEquals(
            expected = APPLICATION_PDF,
            actual = result.contentType
        )
    }

    @Test
    fun dcm() {
        // pattern starts after an offset of 0x80
        var data = ByteArray(0x84) { 0x00 }
        data[0x80] = 0x44
        data[0x81] = 0x49
        data[0x82] = 0x43
        data[0x83] = 0x4D
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.DCM, result)
        assertEquals(
            expected = APPLICATION_DCM,
            actual = result.contentType
        )
    }

    @Test
    fun unknown() {
        val data = byteArrayOf(0x00)
        val result = MimeType.recognizeMimeType(data)

        assertEquals(MimeType.UNKNOWN, result)
        assertTrue(result.contentType.isEmpty())
    }

    companion object {
        private const val IMAGE_TIFF = "image/tiff"
        private const val IMAGE_PNG = "image/png"
        private const val IMAGE_JPG = "image/jpg"
        private const val APPLICATION_PDF = "application/pdf"
        private const val APPLICATION_DCM = "application/dicom"
    }
}

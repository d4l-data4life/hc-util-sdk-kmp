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
    private const val BASE64_ALPHABET: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private const val BASE64_MASK: Byte = 0x3f
    private const val BASE64_PAD: Char = '='

    private fun Int.toBase64(): Char = BASE64_ALPHABET[this]
    private fun ByteArray.getOrZero(index: Int): Int = if (index >= size) 0 else get(index).toInt()

    private fun calcPaddingSize(symbolsLeft: Int): Int {
        return if (symbolsLeft >= 3) {
            0
        } else {
            (3 - symbolsLeft) * 8 / 6
        }
    }

    private fun chopChunk(data: ByteArray, index: Int): Int {
        return (
            (data.getOrZero(index) shl 16)
                or (data.getOrZero(index + 1) shl 8)
                or data.getOrZero(index + 2)
            )
    }

    @ExperimentalStdlibApi
    private fun mask(chunk: Int, padSize: Int, encoded: ArrayList<Byte>) {
        for (i in 3 downTo padSize) {
            val char = (chunk shr (6 * i)) and BASE64_MASK.toInt()
            encoded.add(char.toBase64().code.toByte())
        }
    }

    @ExperimentalStdlibApi
    private fun encodeData(data: ByteArray): ArrayList<Byte> {
        val encoded = ArrayList<Byte>(4 * data.size / 3)
        var index = 0
        while (index < data.size) {
            val padSize = calcPaddingSize(data.size - index)
            val chunk = chopChunk(data, index)
            mask(chunk, padSize, encoded)

            index += 3

            // Fill the pad with '='
            repeat(padSize) { encoded.add(BASE64_PAD.code.toByte()) }
        }

        return encoded
    }

    @ExperimentalStdlibApi
    actual fun encode(data: ByteArray): ByteArray = encodeData(data).toByteArray()

    @ExperimentalStdlibApi
    actual fun encodeToString(data: ByteArray): String {
        val encoded = encode(data)
        return buildString(encoded.size) {
            encoded.forEach { append(it.toChar()) }
        }
    }

    @ExperimentalStdlibApi
    actual fun encodeToString(data: String): String = encodeToString(data.encodeToByteArray())

    actual fun decode(encodedData: ByteArray): ByteArray {
        TODO()
    }

    actual fun decode(encodedData: String): ByteArray {
        TODO()
    }

    actual fun decodeToString(encodedData: String): String {
        TODO()
    }
}

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

package care.data4life.sdk.util.test

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import care.data4life.sdk.config.TestConfig

class CommonResourceLoaderTest {
    @Test
    fun `Given exists is called, with a Path to a Fixture, returns false if the Fixture in Common does not exists`() {
        // Given
        val path = "/somthing.json"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given exists is called, with a Path to a Fixture and a RootPath, returns false if the Fixture in the given Path does not exists`() {
        // Given
        val path = "/somthing.json"
        val root = "/ROOT/"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path, root)

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given exists is called, with a Path to a Fixture, returns true if the Fixture in CommonTest exists`() {
        // Given
        val path = "/somethingElse.json"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path)

        // Then
        assertTrue(result)
    }

    @Test
    fun `Given exists is called, with a Path to a Fixture and a RootPath, returns true if the Fixture in the given Path exists`() {
        // Given
        val root = "./src/jvmTest/resources/"
        val path = "/example.json"

        // When
        val result = CommonResourceLoader(TestConfig.projectDir).exists(path, root)

        // Then
        assertTrue(result)
    }
}

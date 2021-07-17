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

import care.data4life.sdk.util.test.lang.FileNotFoundError

typealias Path = String
typealias AbsolutePath = String

expect class CommonResourceLoader(
    projectDir: AbsolutePath
) {
    fun exists(path: Path, root: Path? = null): Boolean

    @Throws(FileNotFoundError::class)
    fun load(path: Path, root: Path? = null): String
}

// This should be scoped in the ResourceLoader and be private, however this is currently not supported by Kotlin
internal object CommonPathResolver {
    fun resolvePath(
        projectDir: AbsolutePath,
        moduleDir: Path? = null,
        target: Path
    ): AbsolutePath {
        return if (moduleDir is Path) {
            "${projectDir.trimEnd('/')}/${moduleDir.trimEnd('/')}/${target.trimStart('/')}"
        } else {
            "${projectDir.trimEnd('/')}/${Constants.commonRoot.trim('/')}/${target.trimStart('/')}"
        }
    }
}

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

package scripts

/**
 * [Gradle Download Task](https://github.com/michel-kraemer/gradle-download-task)
 *
 * You need to add following dependencies to the buildSrc/build.gradle.kts
 *
 * - implementation("de.undercouch:gradle-download-task:4.1.1")
 *
 *
 * It requires a Environment variable set for GITHUB_USERNAME and GITHUB_REPO_TOKEN, the token should have repo:read scope
 *
 */
plugins {
    id("de.undercouch.download")
}

private val repository = "https://raw.githubusercontent.com/d4l-data4life/hc-gradle-scripts/"
private val branch = "main"
private val path = "buildSrc/src/main/kotlin/scripts"
private val scriptLink = "$repository/$branch/$path"

val scriptFiles = listOf(
    "$scriptLink/dependency-check.gradle.kts",
    "$scriptLink/download-scripts.gradle.kts",
    "$scriptLink/publishing.gradle.kts",
    "$scriptLink/publishing-config.gradle.kts",
    "$scriptLink/quality-spotless.gradle.kts",
    "$scriptLink/versioning.gradle.kts"
)

val downloadGradleScripts by tasks.creating(de.undercouch.gradle.tasks.download.Download::class) {
    group = "download"
    description = "Downloads the latest version of D4L Gradle scripts"

    username(System.getenv("GITHUB_USERNAME"))
    password(System.getenv("GITHUB_REPO_TOKEN"))

    src(scriptFiles)
    dest("${rootProject.rootDir}/buildSrc/src/main/kotlin/scripts/")

    overwrite(true)
}

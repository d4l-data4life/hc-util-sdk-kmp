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

import care.data4life.gradle.util.dependency.d4l
import care.data4life.gradle.util.dependency.ensureKotlinVersion

plugins {
    `kotlin-dsl`

    id("care.data4life.gradle.util.dependency")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    d4l()
}

dependencies {
    implementation(care.data4life.gradle.util.dependency.GradlePlugin.kotlin)
    implementation(care.data4life.gradle.util.dependency.GradlePlugin.android)

    // dependency-updates.gradle.kts
    implementation("com.github.ben-manes:gradle-versions-plugin:0.42.0")
    // download-scripts.gradle.kts
    implementation("de.undercouch:gradle-download-task:5.1.1")
    // publishing.gradle.kts
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.2.0.202206071550-r")
    // quality-spotless.gradle.kts
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.9.0")
    implementation("com.pinterest:ktlint:0.46.1")
    // versioning.gradle.kts
    implementation("com.palantir.gradle.gitversion:gradle-git-version:0.15.0")
}

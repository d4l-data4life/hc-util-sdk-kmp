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

buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven("https://dl.bintray.com/data4life/maven")
    }
    dependencies {
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.gitPublish)
    }
    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }
}

plugins {
    kotlinMultiplatform(false)

    dependencyUpdates()

    id("scripts.versioning")
    id("scripts.publishing")
    id("scripts.quality")

    id("de.undercouch.download") version "4.1.1"
}

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        jcenter()

        maven("https://kotlin.bintray.com/kotlin")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://jitpack.io")
    }

    dependencyLocking {
        lockAllConfigurations()
    }
    val resolveAndLockAll by tasks.registering {
        group = "dependencies"
        doFirst {
            require(gradle.startParameter.isWriteDependencyLocks)
        }
        doLast{
            configurations.filter {
                // Add any custom filtering on the configurations to be resolved
                it.isCanBeResolved
            }.forEach { it.resolve() }
        }
    }
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}

val downloadGradleScripts by tasks.creating(de.undercouch.gradle.tasks.download.Download::class) {
    group = "download"
    description = "Downloads the latest version of D4L Gradle scripts"

    username(
        project.findProperty("gpr.user") as String?
            ?: System.getenv("GITHUB_USERNAME")
    )
    // this needs a GitHub personal access token with repo permission
    password(
        project.findProperty("gpr.key") as String?
            ?: System.getenv("GITHUB_REPO_TOKEN")
    )

    val repository = "https://raw.githubusercontent.com/d4l-data4life/hc-gradle-scripts/"
    val branch = "main"
    val path = "buildSrc/src/main/kotlin/scripts"
    val scriptLink = "$repository/$branch/$path"
    src(
        listOf(
            "$scriptLink/publishing.gradle.kts",
            "$scriptLink/publishing-config.gradle.kts",
            "$scriptLink/quality.gradle.kts",
            "$scriptLink/versioning.gradle.kts"
        )
    )
    dest("${rootProject.rootDir}/buildSrc/src/main/kotlin/scripts/")

    overwrite(true)
}

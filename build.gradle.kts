/*
 * Copyright (c) 2020 D4L data4life gGmbH / All rights reserved.
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
}

plugins {
    kotlinMultiplatform(false)
    dependencyUpdates()
    gitVersioning()
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
}

jgitver {
    strategy(fr.brouillard.oss.jgitver.Strategies.MAVEN)

    policy(closureOf<fr.brouillard.oss.gradle.plugins.JGitverPluginExtensionBranchPolicy> {
        pattern = "release/(.*)"
        transformations = listOf("IGNORE")
    })

    policy(closureOf<fr.brouillard.oss.gradle.plugins.JGitverPluginExtensionBranchPolicy> {
        pattern = "feature/(.*)"
        transformations = listOf("LOWERCASE_EN")
    })

    policy(closureOf<fr.brouillard.oss.gradle.plugins.JGitverPluginExtensionBranchPolicy> {
        pattern = "(main)"
        transformations = listOf("IGNORE")
    })

    nonQualifierBranches = "main"
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.7"
    distributionType = Wrapper.DistributionType.ALL
}

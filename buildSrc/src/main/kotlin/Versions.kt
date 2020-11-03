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

object Versions {

    /**
     *  https://github.com/Kotlin/kotlinx.coroutines
     */
    const val kotlinCoroutines = "1.1.1"

    object GradlePlugins {
        const val kotlin = Versions.kotlin
        const val android = "4.0.1"

        /**
         * [DependencyUpdates](https://github.com/ben-manes/gradle-versions-plugin)
         */
        const val dependencyUpdates = "0.34.0"
    }

    // Kotlin
    // https://github.com/JetBrains/kotlin
    const val kotlin = "1.3.50"
    const val kotlinGradlePlugin = kotlin

    // Java
    const val javaXAnnotation = "3.0.2"

    // Android
    const val androidSupport = "28.0.0"
    const val androidConstraintLayout = "1.1.3"

    // Google
    const val googlePlayServices = "16.0.1"

    // Crypto
    const val bouncyCastle = "1.60"

    // Tink
    /**
     * [tink](https://github.com/google/tink)
     */
    const val tink = "1.2.2"

    // Authorization
    /**
     * [appAuth](https://github.com/openid/AppAuth-Android)
     */
    const val appAuth = "0.7.1"

    /**
     * [appAuthPatch](https://github.com/gesundheitscloud/AppAuth-Android)
     */
    const val appAuthPatch = "9e3cc033ff"

    /**
     * [scribe](https://github.com/scribejava/scribejava)
     */
    const val scribe = "6.1.0"

    // Network
    /**
     * [okHttp](https://github.com/square/okhttp)
     */
    const val okHttp = "4.3.1"

    /**
     *
     *[retrofit](https://github.com/square/retrofit)
     */
    const val retrofit = "2.5.0"

    // Data
    /**
     * [moshi](https://github.com/square/moshi)
     */
    const val moshi = "1.8.0"

    /**
     * [HC-FHIR-SDK-JAVA](https://github.com/d4l-data4life/hc-fhir-sdk-java)
     */
    const val fhir = "0.6.0"


    // Date
    /**
     * [ThreeTen Backport](https://www.threeten.org/threetenbp)
     */
    const val threeTenBP = "1.3.8"

    /**
     * [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)
     */
    const val threeTenABP = "1.1.1"

    // Injection
    const val koin = "1.0.1"

    // Rx
    /**
     * [RxJava](https://github.com/ReactiveX/RxJava)
     */
    const val rxJava = "2.2.4"

    /**
     * [RxAndroid](https://github.com/ReactiveX/RxAndroid)
     */
    const val rxAndroid = "2.1.0"

    // Ui
    /**
     * [clikt](https://github.com/ajalt/clikt)
     */
    const val clikt = "1.6.0"


    // Junit Test
    const val testJUnit = "4.13"

    /**
     * [mockk](http://mockk.io)
     */
    const val testMockk = "1.9.3"

    const val testTruth = "0.42"

    /**
     * [mockito](https://github.com/mockito/mockito)
     */
    const val testMockito = "2.23.0"

    const val testJsonAssert = "1.5.0"

    /**
     * [robolectric](http://robolectric.org/)
     */
    const val robolectric = "4.1"

    // Android Test
    const val androidTest = "1.0.2"
    const val androidTestEspresso = "3.0.2"
}

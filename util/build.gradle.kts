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

plugins {
    kotlinMultiplatform()

    // Android
    androidLibrary()

    // Publish
    id("maven-publish")
}

version = LibraryConfig.version
group = LibraryConfig.group


kotlin {
    android {
        publishLibraryVariants("release")
//        artifacts {
//            archives androidSourcesJar
//            archives androidJavadocJar
//        }
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.stdlibCommon)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.testCommon)
                implementation(Dependencies.multiplatform.kotlin.testCommonAnnotations)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.stdlibAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.testJvm)
                implementation(Dependencies.multiplatform.kotlin.testJvmJunit)

                implementation(Dependencies.android.robolectric)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.stdlibJdk8)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Dependencies.multiplatform.kotlin.testJvm)
                implementation(Dependencies.multiplatform.kotlin.testJvmJunit)
            }
        }

    }
}

android {
    compileSdkVersion(LibraryConfig.android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(LibraryConfig.android.minSdkVersion)
        targetSdkVersion(LibraryConfig.android.targetSdkVersion)

        versionCode = LibraryConfig.android.versionCode
        versionName = LibraryConfig.android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments(
            mapOf(
                "clearPackageData" to "true"
            )
        )
    }

    resourcePrefix(LibraryConfig.android.resourcePrefix)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.setSrcDirs(setOf("src/androidMain/kotlin"))
            res.setSrcDirs(setOf("src/androidMain/res"))
        }

        getByName("test") {
            java.setSrcDirs(setOf("src/androidTest/kotlin"))
            res.setSrcDirs(setOf("src/androidTest/res"))
        }
    }
}

configure<PublishingExtension> {
    publications {
        withType<MavenPublication> {
            groupId = "${LibraryConfig.publish.groupId}.${LibraryConfig.publish.artifactId}"
            artifactId = LibraryConfig.publish.artifactId
            version = LibraryConfig.publish.version

//            if (publication.name == "androidRelease") {
//                publication.artifactId = "$project.name-android"
//            } else if (publication.name == "metadata") {
//                publication.artifactId = "$project.name-common"
//            } else if (publication.name == "jvm") {
//                publication.artifactId = "$project.name-jvm"
//            } else if (publication.name == 'kotlinMultiplatform') {
//                publication.artifactId = "$project.name"
//            }

            pom {
                name.set(LibraryConfig.publish.name)
                url.set(LibraryConfig.publish.url)
                inceptionYear.set(LibraryConfig.publish.year)

                licenses {
                    license {
                        name.set(LibraryConfig.publish.licenseName)
                        url.set(LibraryConfig.publish.licenseUrl)
                        distribution.set(LibraryConfig.publish.licenseDistribution)
                    }
                }

                developers {
                    developer {
                        id.set(LibraryConfig.publish.developerId)
                        name.set(LibraryConfig.publish.developerName)
                        email.set(LibraryConfig.publish.developerEmail)
                    }
                }

                scm {
                    connection.set(LibraryConfig.publish.scmConnection)
                    developerConnection.set(LibraryConfig.publish.scmDeveloperConnection)
                    url.set(LibraryConfig.publish.scmUrl)
                }
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${LibraryConfig.githubOwner}/${LibraryConfig.githubRepository}")
            credentials {
                username = (project.findProperty("gpr.user") ?: System.getenv("PACKAGE_REGISTRY_USERNAME")).toString()
                password = (project.findProperty("gpr.key") ?: System.getenv("PACKAGE_REGISTRY_TOKEN")).toString()
            }
        }
    }
}

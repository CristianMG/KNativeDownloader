
plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        publishAllLibraryVariants()
    }
    iosArm64("ios")
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("io.ktor:ktor-client-core:1.2.5")
                implementation ("com.github.aakira:napier:1.0.0")
            }
        }

        val iosMain by getting{
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.2.5")
                implementation ("io.ktor:ktor-client-ios:1.2.5")
                implementation ("com.github.aakira:napier-ios:1.0.0")

            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("io.ktor:ktor-client-android:1.2.5")
                implementation ("io.ktor:ktor-client-okhttp:1.2.5")
                implementation ("com.github.aakira:napier-android:1.0.0")
            }
        }

    }
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(15)
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}


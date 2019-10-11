plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
   // android {
     //   publishAllLibraryVariants()
    //}
    jvm()
   // iosArm64("ios")
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
                implementation("com.github.aakira:napier:1.0.0")
                api ("io.ktor:ktor-client-mock-native:1.2.5")
            }
        }

      /*  val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.2.5")
                implementation("com.github.aakira:napier-ios:1.0.0")

            }
        }*/

    /*    val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation ("io.ktor:ktor-client-okhttp:1.2.5")
                implementation("com.github.aakira:napier-android:1.0.0")
            }
        }
*/
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation ("com.github.aakira:napier-jvm:1.0.0")
                implementation("io.ktor:ktor-client-okhttp:1.2.5")
                implementation("org.apache.commons:commons-io:1.3.2")

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.12")
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


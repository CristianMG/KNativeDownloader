plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("knativedownloaderdatabase") {
        packageName = "com.cristianmg.knativedownloader.db"
    }
}

//TODO work around for https://youtrack.jetbrains.com/issue/KT-27170
configurations {
    compileClasspath
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
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.2")

                implementation("com.github.aakira:napier:1.0.0")
                api("io.ktor:ktor-client-mock-native:1.2.5")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-test-common:1.3.50")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:1.3.50")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("com.github.aakira:napier-jvm:1.0.0")
                implementation("io.ktor:ktor-client-okhttp:1.2.5")
                implementation("org.apache.commons:commons-io:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
                implementation ("com.squareup.sqldelight:sqlite-driver:1.2.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.12")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.2")
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

    packagingOptions {
        exclude ("META-INF/*.kotlin_module")
    }
}




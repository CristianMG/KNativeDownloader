pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.android.library" -> useModule("com.android.tools.build:gradle:${requested.version}")
                "com.jfrog.bintray" -> useModule("com.jfrog.bintray.gradle:gradle-bintray-plugin:${requested.version}")
            }
        }
    }

    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        jcenter()
    }
}
include(":KNativeDownloader")
enableFeaturePreview("GRADLE_METADATA")

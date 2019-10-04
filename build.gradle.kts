
plugins {
    kotlin("multiplatform").version("1.3.50").apply(false)
    id("com.android.library").version("3.4.1").apply(false)
}

subprojects {
    repositories {
        google()
        jcenter()
    }

    tasks.withType(AbstractTestTask::class) {
        testLogging {
            showStandardStreams = true
            events("passed", "failed")
        }
    }

    // workaround for https://youtrack.jetbrains.com/issue/KT-27170
    configurations.create("compileClasspath")
}

rootProject.name = "cloche-example"

pluginManagement {
    repositories {
        maven(url = "https://maven.msrandom.net/repository/cloche")
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.3.10"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

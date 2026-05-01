pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lesson4"
include(":app")
include(":viewbinding_1")
include(":thread_2_5")
include(":data_thread_3_1")
include(":looper_3_2")
include(":cryptoloader_3_3")
include(":workmanager_5_1")
include(":mireaproject_6")
include(":serviceapp_4_1")

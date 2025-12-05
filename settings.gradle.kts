// File: priachoki/ca3-nutriscalp-app/CA3-NutriScalp-App-5fcd453eccfd7c8230d55b11f58cec15baf9de48/settings.gradle.kts

pluginManagement {
    repositories {
        google() // 🚨 FIX: Removed restrictive 'content' filter
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NutriScalp"
include(":app")
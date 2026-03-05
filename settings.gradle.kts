pluginManagement {
    repositories {
        google()
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

rootProject.name = "NextGenIPTV"

// Phase 1: nur app
include(":app")

// Ab Phase 3 schrittweise aktivieren:
// include(":core:core-network")
// include(":core:core-database")
// include(":core:core-player")
// include(":core:core-datastore")
// include(":core:core-ui")
// include(":feature:feature-onboarding")
// include(":feature:feature-dashboard")
// include(":feature:feature-player")
// include(":feature:feature-live-tv")
// include(":feature:feature-vod")
// include(":feature:feature-series")
// include(":feature:feature-settings")

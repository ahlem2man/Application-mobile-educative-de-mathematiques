pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()  // Requis pour Android
        mavenCentral()  // Requis pour beaucoup de dépendances
        maven(url = "https://jitpack.io")  // Pour les dépendances GitHub
    }
}

rootProject.name = "MathematicsClassProject"

include(":app")

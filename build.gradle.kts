plugins {
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.ANDROID_LIBRARY) apply false
}
// all projects = root project + sub projects
allprojects {

    // Gradle dependency locking - lock all configurations of the app
    // More: https://docs.gradle.org/current/userguide/dependency_locking.html
    dependencyLocking {
        lockAllConfigurations()
    }
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:1.6.21")
    }
}
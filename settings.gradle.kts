rootProject.buildFileName = "build.gradle.kts"

enableFeaturePreview("VERSION_CATALOGS")

// Set single lock file (gradle.lockfile)
// This preview feature should be enabled by default in Gradle 7
// More: https://docs.gradle.org/current/userguide/dependency_locking.html#single_lock_file_per_project
enableFeaturePreview("ONE_LOCKFILE_PER_PROJECT")

include(
    ":app",

    // Features
    ":feature_vehicle_listing",

    // Libraries
    ":library_ui",
    ":library_utils",
)

pluginManagement {
    plugins {
        // See Dependency management section in README.md
        // https://github.com/igorwojda/android-showcase#dependency-management
        val agpVersion: String by settings
        id("com.android.application") version "7.2.0"
        id("com.android.library") version "7.2.0"

        val kotlinVersion: String by settings
        id("org.jetbrains.kotlin.android") version "1.6.21"
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library" -> {
                    val agpCoordinates: String by settings
                    useModule(agpCoordinates)
                }
            }
        }
    }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }

    versionCatalogs {
        create("libs") {
            val kotlinVersion: String by settings
            version("kotlin", kotlinVersion)

            // Android Core
            alias("androidx-core-coreKtx").to("androidx.core", "core-ktx").version("1.7.0")
            alias("androidx-core-splashScreen").to("androidx.core", "core-splashscreen").version("1.0.0-beta01")

            // Android Lifecycle
            version("lifecycle", "2.4.1")
            alias("androidx-lifecycle-runtimeKtx").to("androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle")
            alias("androidx-lifecycle-viewModelKtx").to("androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")

            // Android UI
            alias("androidx-appcompat").to("androidx.appcompat:appcompat:1.3.1")
            alias("google-material").to("com.google.android.material", "material").version("1.5.0")
            alias("androidx-fragmentKtx").to("androidx.fragment:fragment-ktx:1.4.1")
            alias("androidx-recyclerview").to("androidx.recyclerview:recyclerview:1.2.1")

            // Database
            alias("androidx-room").to("androidx.room:room-ktx:2.4.2")

            // ViewBinding
            alias("zhuinden-fragmentViewBindingDelegate").to("com.github.Zhuinden", "fragmentviewbindingdelegate-kt").version("1.0.0")

            // Kotlinx
            alias("kotlinx-coroutinesAndroid").to("org.jetbrains.kotlinx", "kotlinx-coroutines-android").version("1.6.0")

            // Hilt
            version("hilt", "2.40.4")
            alias("hilt-android").to("com.google.dagger", "hilt-android").versionRef("hilt")
            alias("hilt-compiler").to("com.google.dagger", "hilt-android-compiler").versionRef("hilt")


            // Retrofit + Okhttp
            version("retrofit", "2.9.0")
            alias("retrofit").to("com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            alias("retrofit-gson").to("com.squareup.retrofit2","converter-gson").versionRef("retrofit")
            alias("okhttp-interceptor").to("com.squareup.okhttp3:logging-interceptor:4.9.3")

            // Timber
            alias("timber").to("com.jakewharton.timber", "timber").version("5.0.1")

            //Coil
            alias("coil").to("io.coil-kt:coil:2.0.0-rc03")

            // Testing
            alias("junit5").to("org.junit.jupiter:junit-jupiter:5.7.1")

            // Mocking library
            alias("mockk").to("io.mockk:mockk:1.12.3")
        }
    }
}

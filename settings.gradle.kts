rootProject.buildFileName = "build.gradle.kts"

enableFeaturePreview("VERSION_CATALOGS")

include(
    ":app",

    // Features
    ":feature_vehicle_listing",

    // Libraries
    ":library_ui",
    ":library_network",
)

pluginManagement {
    plugins {
        // See Dependency management section in README.md
        // https://github.com/igorwojda/android-showcase#dependency-management
        val agpVersion: String by settings
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion

        val kotlinVersion: String by settings
        id("org.jetbrains.kotlin.android") version kotlinVersion
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

            // Kotlin
            version("nav_version", "2.4.2")
            alias("navigation-fragment").to("androidx.navigation", "navigation-fragment-ktx").versionRef("nav_version")
            alias("navigation-ui").to("androidx.navigation", "navigation-ui-ktx").versionRef("nav_version")

            // Room
            version("room", "2.4.2")
            alias("androidx-room").to("androidx.room","room-runtime").versionRef("room")
            alias("androidx-room-compiler").to("androidx.room","room-compiler").versionRef("room")
            alias("androidx-room-ktx").to("androidx.room", "room-ktx").versionRef("room")

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
            alias("moshi").to("com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            alias("okhttp-interceptor").to("com.squareup.okhttp3:logging-interceptor:4.9.3")

            // Timber
            alias("timber").to("com.jakewharton.timber", "timber").version("5.0.1")

            //Glide
            alias("coil").to("io.coil-kt:coil:2.1.0")

            // Testing
            alias("junit5").to("org.junit.jupiter:junit-jupiter:5.7.1")
            alias("jupiter-engine").to("org.junit.jupiter:junit-jupiter-engine:5.7.1")
            alias("okhttp3-mock").to("com.squareup.okhttp3:mockwebserver:4.9.3")
            alias("kotlinx-coroutines-test").to("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
            alias("turbine").to("app.cash.turbine:turbine:0.8.0")

            // Mocking library
            alias("mockk").to("io.mockk:mockk:1.12.3")
        }
    }
}

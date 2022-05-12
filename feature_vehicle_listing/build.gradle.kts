plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_PARCELIZE)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT)
    id(GradlePluginId.NAVIGATION_SAFEARGS)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":library_ui"))
    implementation(project(":library_utils"))


    // Android
    implementation(libs.androidx.core.coreKtx)

    // Kotlin
    implementation(libs.kotlinx.coroutinesAndroid)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    // Retrofit
    implementation(libs.retrofit)
    api(libs.retrofit.gson)

    //Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //Coil
    implementation(libs.coil)

    // Logging
    implementation(libs.timber)

    // Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
}
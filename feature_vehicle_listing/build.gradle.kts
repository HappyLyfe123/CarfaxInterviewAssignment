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

tasks.withType<Test>{
    useJUnitPlatform()
}

dependencies {
    implementation(project(":library_ui"))
    implementation(project(":library_network"))


    // Android
    implementation(libs.androidx.core.coreKtx)

    // Kotlin
    implementation(libs.kotlinx.coroutinesAndroid)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    // Retrofit
    api(libs.retrofit)
    api(libs.moshi)

    //Navigation
    api(libs.navigation.fragment)
    api(libs.navigation.ui)

    //Glide
    implementation(libs.glide)

    // Logging
    implementation(libs.timber)

    // Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava3)
    kapt(libs.androidx.room.compiler)

    //ReactiveX
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.rxkotlin)

    //Testing
    testImplementation(libs.mockk)
    testImplementation(libs.junit5)
    testImplementation(libs.okhttp3.mock)
    testRuntimeOnly(libs.jupiter.engine)

}
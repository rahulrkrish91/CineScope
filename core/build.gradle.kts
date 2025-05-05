plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.malabar.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.lifecycle.common.java8)

    api(libs.navigation.compose)

    api(libs.room.runtime)
    ksp(libs.room.compiler)
    api(libs.room.ktx)

    api(libs.retrofit)
    api(libs.retrofit.gson.converter)

    api(platform(libs.okhttp.bom))
    api(libs.okhttp)
    api(libs.logging.interceptor)

    api(libs.coroutine.core)
    api(libs.coroutine.android)

    api(libs.arrow.core)

    api(libs.coil.compose)
    api(libs.coil.network)

    api(libs.koin.android)
    api(libs.koin.core)
    api(libs.koin.compose)

    api(libs.youtube.player)

    api(libs.lottie)

    api(libs.firebase.auth)
    api(libs.androidx.credentials)
    api(libs.androidx.credentials.play.services.auth)
    api(libs.googleid)

    api(platform(libs.firebase.bom))
    api(libs.firebase.crashlytics)
    api(libs.firebase.analytics)

    api(libs.app.update)
}
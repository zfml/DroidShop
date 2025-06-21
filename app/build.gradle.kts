plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.schoolproject.droidshop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.schoolproject.droidshop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)




    implementation(libs.android.datastore.preferences)

    implementation(libs.hilt.android)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore.ktx)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.material3)

    implementation(libs.coil.compose)

    implementation(libs.coil.network.okhttp)




//    implementation(libs.firebase.functions.ktx)
//    implementation(libs.kotlinx.coroutines.play.services)

    // Room dependencies
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)  // Use kapt for Kotlin annotation processing
    implementation(libs.room.ktx)  // Optional: For Kotlin extensions

    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.core.splashscreen)


    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.material.icons.extended)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.nl.dionsegijn.konfetti.compose)

    implementation(libs.accompanist.pager)

    implementation(libs.lottie.compose)




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.example.nutriscalp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.nutriscalp"
        minSdk = 24
        targetSdk = 36
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3)

    // NEW ARCHITECTURE AND UTILITY DEPENDENCIES
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Architecture Components
    implementation(libs.androidx.navigation.compose) // Navigation
    implementation(libs.coil.compose) // Load and Display Images using Coil
    implementation(libs.androidx.datastore.preferences) // Use DataStore

    // NEW NETWORKING DEPENDENCIES
    implementation(libs.retrofit.core) // Getting Data from Internet using Retrofit
    implementation(libs.retrofit.converter.gson) // Getting Data from Internet using Retrofit

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.compose.ui.icons.extended)

    testImplementation(libs.junit)


}
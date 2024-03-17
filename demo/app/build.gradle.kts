@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "demo.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "demo.app"

        minSdk = 21
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"


        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.compose.activity)
    implementation(libs.compose.material3)

    implementation(libs.compose.uitooling.preview)

    debugImplementation(libs.compose.uitooling)
    debugImplementation(libs.compose.uitest.manifest)
}

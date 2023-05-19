@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "au.sgallitz.nitrogen.demo"
    compileSdk = 33

    defaultConfig {
        applicationId = "au.sgallitz.nitrogen.demo"

        minSdk = 21
        targetSdk = 33

        versionCode = 1
        versionName = "1.0"


        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))

    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

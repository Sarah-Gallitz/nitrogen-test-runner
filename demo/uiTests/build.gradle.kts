plugins {
    alias(libs.plugins.nitrogen.test)
    alias(libs.plugins.kotlin.android)
}

android {
    defaultConfig {
        testApplicationId = "demo.uiTests"
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
}

dependencies {
    implementation(project(":demo:app"))

    implementation(embeddedKotlin("test-junit"))

    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.espresso)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.uitest)

    implementation(project(":nitrogen"))
}

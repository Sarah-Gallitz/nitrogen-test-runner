plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")

    id("au.sgallitz.nitrogen")
}

android {
    targetProjectPath = ":demo:app"

    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testApplicationId = "au.sgallitz.nitrogen.demo.uiTests"
    }
}

dependencies {
    implementation(project(":demo:app"))

    implementation(embeddedKotlin("test-junit"))
    implementation("androidx.test:runner:1.5.2")
    implementation("androidx.test:rules:1.5.0")

    implementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("androidx.compose:compose-bom:2023.01.00"))
    implementation("androidx.compose.ui:ui-test-junit4")
}

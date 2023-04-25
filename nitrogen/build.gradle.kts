plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        namespace = "au.sgallitz.nitrogen"
        compileSdk = 33
    }
}

dependencies {
    implementation("androidx.test:runner:1.5.2")
    implementation("androidx.test:rules:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    compileOnly("org.robolectric:robolectric:4.9.2")
}

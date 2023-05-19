@file:Suppress("UnstableApiUsage")

plugins {
    id("kotlin")
    id("com.gradle.plugin-publish")
    id("signing")
}

group = "tech.sarahgallitz"
version = "0.0.1"

gradlePlugin {
    website.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner")
    vcsUrl.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner")

    plugins {
        create("nitrogenPlugin") {
            id = "tech.sarahgallitz.nitrogen-plugin"

            displayName = "Nitrogen Test Runner (Android)"
            description = "A test runner for android that allows targeting JVM or Device.\n\nThis project is in alpha and not ready for use. Check back soon!"
            tags.set(listOf("nitrogen", "android", "uitest", "testing", "espresso", "androidxtest"))

            implementationClass = "tech.sarahgallitz.nitrogen.plugin.NitrogenTestPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation("com.android.tools.build:gradle-api:8.0.1")
}

@file:Suppress("UnstableApiUsage")

plugins {
    id("kotlin")
    alias(libs.plugins.gradle.publish)
    id("signing")
}

group = "tech.sarahgallitz"
version = "0.0.4"

gradlePlugin {
    website.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner")
    vcsUrl.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner")

    plugins {
        create("nitrogenPlugin") {
            id = "tech.sarahgallitz.nitrogen-plugin"

            displayName = "Nitrogen Test Runner (Android)"
            description =
                "A test runner for android that allows targeting JVM or Device.\n\nThis project is in alpha and not ready for use. Check back soon!"
            tags.set(listOf("nitrogen", "android", "uitest", "testing", "espresso", "androidxtest"))

            implementationClass = "tech.sarahgallitz.nitrogen.plugin.NitrogenTestPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation(libs.android.gradle.tools)
}

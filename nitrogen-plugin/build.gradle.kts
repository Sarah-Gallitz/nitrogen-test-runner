plugins {
    id("kotlin")
    `java-gradle-plugin`
    `maven-publish`
}

group = "au.sgallitz.nitrogen"
version = "0.0.2"

gradlePlugin {
    plugins {
        create("nitrogenPlugin") {
            id = "au.sgallitz.nitrogen"
            implementationClass = "au.sgallitz.nitrogen.plugin.NitrogenTestPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation("com.android.tools.build:gradle-api:7.4.2")
}

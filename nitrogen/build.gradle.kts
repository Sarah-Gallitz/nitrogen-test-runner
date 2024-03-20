plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("signing")
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "Sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials(PasswordCredentials::class)
            }
        }

        publications {
            create<MavenPublication>("nitrogen") {
                from(components["release"])

                groupId = "tech.sarahgallitz"
                version = "0.0.4"
                artifactId = "nitrogen"

                pom {
                    name.set("Nitrogen Test Runner")
                    description.set("A test runner for android that allows targeting JVM or Device")
                    url.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("http://www.opensource.org/licenses/mit-license.php")
                        }
                    }

                    developers {
                        developer {
                            id.set("Sarah-Gallitz")
                            name.set("Sarah-Jane Gallitz")
                            email.set("sarah.gallitz.devs@gmail.com")
                            organizationUrl.set("http://sarahgallitz.tech")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/Sarah-Gallitz/nitrogen-test-runner.git")
                        developerConnection.set("scm:git:ssh://github.com:Sarah-Gallitz/nitrogen-test-runner.git")
                        url.set("https://github.com/Sarah-Gallitz/nitrogen-test-runner/tree/master")
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications["nitrogen"])
    }
}

android {
    defaultConfig {
        namespace = "tech.sarahgallitz.nitrogen"
        compileSdk = 33
        minSdk = 21
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.junit)

    api(platform(libs.compose.bom))
    implementation(libs.compose.uitest)

    compileOnly(libs.robolectric)
    compileOnly(libs.roborazzi)
    compileOnly(libs.roborazzi.compose)

    implementation(libs.screenshots.falcon)
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
                version = "0.0.1"
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
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("androidx.test:runner:1.5.2")
    implementation("androidx.test:rules:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    compileOnly("org.robolectric:robolectric:4.9.2")
}

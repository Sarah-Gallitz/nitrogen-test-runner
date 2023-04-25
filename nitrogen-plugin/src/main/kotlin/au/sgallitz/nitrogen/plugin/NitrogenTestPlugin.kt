package au.sgallitz.nitrogen.plugin

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class NitrogenTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val config = NitrogenTestConfiguration(project)
        if (config.isRunningOnJVM) {
            project.plugins.apply("com.android.library")

            project.extensions.getByType(LibraryExtension::class.java).apply {
                project.evaluationDependsOn(config.targetProjectPath)

                val androidConfig = config.androidConfiguration

                compileSdk = compileSdk ?: androidConfig.compileSdk

                defaultConfig {
                    minSdk = minSdk ?: androidConfig.defaultConfig.minSdk

                    testOptions {
                        unitTests {
                            isIncludeAndroidResources = true
                        }
                    }
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }

                sourceSets {
                    getByName("test") {
                        it.kotlin.srcDir("src/main/kotlin")
                        it.java.srcDir("src/main/kotlin")
                    }
                }

                namespace = namespace ?: androidConfig.namespace
            }

            // TODO: This could be better
            project.tasks.create("copyTestManifest") {
                val manifest = config.targetApp.file("src/main/AndroidManifest.xml").readText()
                val testManifest = project.file("src/main/AndroidManifest.xml")
                testManifest.createNewFile()
                testManifest.writeText(
                    manifest.replace(
                        ("(?s)<application.*?>").toRegex(),
                        "<application android:usesCleartextTraffic=\"true\">"
                    )
                )
            }

            project.dependencies.add(
                "implementation",
                "org.robolectric:robolectric:4.9.2"
            )
            project.afterEvaluate {
                project.tasks.getByName("test").dependsOn("copyTestManifest")
            }
        } else {
            project.plugins.apply("com.android.test")

            project.extensions.getByType(TestExtension::class.java).apply {
                targetProjectPath = config.targetProjectPath

                val androidConfig = config.androidConfiguration

                compileSdk = compileSdk ?: androidConfig.compileSdk

                defaultConfig {
                    minSdk = minSdk ?: androidConfig.defaultConfig.minSdk
                    targetSdk = targetSdk ?: androidConfig.defaultConfig.targetSdk

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                namespace = namespace ?: androidConfig.namespace
            }
        }
    }
}

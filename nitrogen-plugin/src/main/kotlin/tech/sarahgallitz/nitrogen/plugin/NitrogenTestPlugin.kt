package tech.sarahgallitz.nitrogen.plugin

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

                sourceSets {
                    getByName("test") {
                        it.kotlin.srcDir("src/main/kotlin")
                        it.java.srcDir("src/main/kotlin")
                    }
                }

                namespace = namespace ?: androidConfig.namespace
            }

            // TODO: This could be better
            project.tasks.create("createTestManifest") {
                it.doFirst {
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
            }

            project.findProject(config.targetProjectPath)?.tasks
                ?.filter { it.name.startsWith("process") && it.name.endsWith("Manifest") }
                ?.forEach { it.finalizedBy("${project.path}:createTestManifest") }

            project.dependencies.add(
                "implementation",
                "org.robolectric:robolectric:4.9.2"
            )
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

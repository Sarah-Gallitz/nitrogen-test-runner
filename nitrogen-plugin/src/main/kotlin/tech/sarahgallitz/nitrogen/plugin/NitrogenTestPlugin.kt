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
                        it.kotlin.srcDir("src/main/java")
                        it.java.srcDir("src/main/java")
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
                            "<application android:usesCleartextTraffic=\"true\" \nandroid:theme=\"@android:style/Theme.DeviceDefault.Light.NoActionBar\">"
                        )
                    )
                }
            }

            project.findProject(config.targetProjectPath)?.tasks
                ?.filter { it.name.startsWith("process") && it.name.endsWith("Manifest") }
                ?.forEach { it.finalizedBy("${project.path}:createTestManifest") }

            project.plugins.apply("io.github.takahirom.roborazzi")

            project.dependencies.add(
                "implementation",
                "org.robolectric:robolectric:[4.10.0,5.0.0)!!4.10.3"
            )
            project.dependencies.add(
                "implementation",
                "io.github.takahirom.roborazzi:roborazzi:[1.0.0,2.0.0)!!1.10.1"
            )
            project.dependencies.add(
                "implementation",
                "io.github.takahirom.roborazzi:roborazzi-compose:[1.0.0,2.0.0)!!1.10.1"
            )
            project.dependencies.add(
                "implementation",
                "com.jraska:falcon:[2.0.0,3.0.0)!!2.2.0"
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

            project.dependencies.add(
                "compileOnly",
                "org.robolectric:robolectric:[4.10.0,5.0.0)!!4.10.3"
            )
            project.dependencies.add(
                "compileOnly",
                "io.github.takahirom.roborazzi:roborazzi:[1.0.0,2.0.0)!!1.10.1"
            )
            project.dependencies.add(
                "compileOnly",
                "io.github.takahirom.roborazzi:roborazzi-compose:[1.0.0,2.0.0)!!1.10.1"
            )
        }
    }
}

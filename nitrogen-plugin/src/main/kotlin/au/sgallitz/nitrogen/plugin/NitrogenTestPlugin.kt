package au.sgallitz.nitrogen.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.TestExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.c

class NitrogenTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val config = NitrogenTestConfiguration(project)
        if (config.isRunningOnJVM) {
            project.plugins.apply("com.android.library")

            project.extensions.getByType(LibraryExtension::class.java).apply {

                project.evaluationDependsOn(config.targetProjectPath)

                val targetApp = project.getTargetAndroidApp(config.targetProjectPath)

                val androidConfig = targetApp.getAndroidApplicationExtension()

                compileSdk = compileSdk ?: androidConfig.compileSdk

                defaultConfig {
                    minSdk = minSdk ?: androidConfig.defaultConfig.minSdk
                }

                namespace = namespace ?: androidConfig.namespace
            }
        } else {
            project.plugins.apply("com.android.test")

            project.extensions.getByType(TestExtension::class.java).apply {
                val targetApp = project.getTargetAndroidApp(config.targetProjectPath)

                targetProjectPath = targetApp.path

                val androidConfig = targetApp.getAndroidApplicationExtension()

                compileSdk = compileSdk ?: androidConfig.compileSdk

                defaultConfig {
                    minSdk = minSdk ?: androidConfig.defaultConfig.minSdk
                    targetSdk = targetSdk ?: androidConfig.defaultConfig.targetSdk
                }

                namespace = namespace ?: androidConfig.namespace
            }
        }
    }

    private fun Project.getTargetAndroidApp(path: String?): Project {
        val nitrogenTargetProjectPath = path
        return if (nitrogenTargetProjectPath == null) {
            throw IllegalArgumentException("targetProjectPath must be configured")
        } else {
            val targetProject = project.rootProject.findProject(nitrogenTargetProjectPath)
            if (targetProject == null || !targetProject.plugins.hasPlugin("com.android.application")) {
                throw IllegalArgumentException("targetProjectPath must be an android application module")
            } else {
                targetProject
            }
        }
    }

    private fun Project.getAndroidApplicationExtension(): ApplicationExtension {
        return this.extensions.getByType(ApplicationExtension::class.java)
            ?: throw NullPointerException()
    }
}

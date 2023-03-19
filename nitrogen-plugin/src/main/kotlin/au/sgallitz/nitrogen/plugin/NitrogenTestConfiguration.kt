package au.sgallitz.nitrogen.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

class NitrogenTestConfiguration(private val project: Project) {
    private val config: Map<String, String>

    init {
        config = project.file("nitrogenTest.config").readText().split("\n")
            .filter { it.contains("=") }
            .associate { it.split("=")[0].trim() to it.split("=")[1].trim() }
    }

    val isRunningOnJVM = config["isRunningOnJVM"].toBoolean()
    val targetProjectPath = config["targetProjectPath"] ?: ""

    val targetApp: Project
        get() = if (targetProjectPath.isBlank()) {
            throw IllegalArgumentException("targetProjectPath must be configured")
        } else {
            val targetProject = project.rootProject.findProject(targetProjectPath)
            if (targetProject == null || !targetProject.plugins.hasPlugin("com.android.application")) {
                throw IllegalArgumentException("targetProjectPath must be an android application module")
            } else {
                targetProject
            }
        }

    val androidConfiguration: ApplicationExtension
        get() = targetApp.extensions.getByType(ApplicationExtension::class.java)
            ?: throw NullPointerException()
}

package au.sgallitz.nitrogen.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import java.io.FileNotFoundException

class NitrogenTestConfiguration(private val project: Project) {
    private val config: Map<String, String>
    private val configFileName = "nitrogen.config"

    init {
        val configFile = if (project.file(configFileName).exists()) {
            project.file(configFileName)
        } else if (project.rootProject.file(configFileName).exists()) {
            project.rootProject.file(configFileName)
        } else {
            throw FileNotFoundException("Nitrogen plugin requires a $configFileName file")
        }

        config = configFile.readText().split("\n")
            .filter { it.contains("=") }
            .associate { it.split("=")[0].trim() to it.split("=")[1].trim() }
    }

    val isRunningOnJVM = config["isRunningOnJVM"].toBoolean()
    val targetProjectPath = config["targetProjectPath"] ?: ""

    val targetApp: Project
        get() = if (targetProjectPath.isBlank()) {
            throw IllegalArgumentException("Nitrogen $configFileName must configure the targetProjectPath")
        } else {
            val targetProject = project.rootProject.findProject(targetProjectPath)
            if (targetProject == null || !targetProject.plugins.hasPlugin("com.android.application")) {
                throw IllegalArgumentException("Nitrogen $configFileName targetProjectPath must be of type android application module")
            } else {
                targetProject
            }
        }

    val androidConfiguration: ApplicationExtension
        get() = targetApp.extensions.getByType(ApplicationExtension::class.java)
            ?: throw NullPointerException()
}

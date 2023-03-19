package au.sgallitz.nitrogen.plugin

import org.gradle.api.Project

class NitrogenTestConfiguration(project: Project) {
    private val config: Map<String, String>

    init {
        config = project.file("nitrogenTest.config").readText().split("\n")
            .filter { it.contains("=") }
            .associate { it.split("=")[0].trim() to it.split("=")[1].trim() }
    }

    val isRunningOnJVM = config["isRunningOnJVM"].toBoolean()
    val targetProjectPath = config["targetProjectPath"] ?: ""
}

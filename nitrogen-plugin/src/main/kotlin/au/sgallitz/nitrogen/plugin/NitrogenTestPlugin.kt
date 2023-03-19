package au.sgallitz.nitrogen.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class NitrogenTestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("XXXXXX Hey from nitrogen")
    }
}

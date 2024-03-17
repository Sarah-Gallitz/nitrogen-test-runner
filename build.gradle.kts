import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.nitrogen.test) apply false
    alias(libs.plugins.gradle.publish) apply false
    alias(libs.plugins.roborazzi) apply false
}

allprojects {
    afterEvaluate {
        extensions.findByType<BaseExtension>()?.apply {
            defaultConfig {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }

        tasks.withType<JavaCompile>().configureEach {
            sourceCompatibility = JavaVersion.VERSION_11.majorVersion
            targetCompatibility = JavaVersion.VERSION_11.majorVersion
        }
    }
}

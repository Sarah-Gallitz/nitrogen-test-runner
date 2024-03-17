package tech.sarahgallitz.nitrogen.environment

import androidx.test.platform.app.InstrumentationRegistry

open class NitrogenConfiguration {
    fun getOnDeviceScreenshotsDir(): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.filesDir.absolutePath + "/test-screenshots/"
    }

    fun getJvmScreenshotsDir(): String {
        return "build/outputs/test-screenshots/"
    }
}

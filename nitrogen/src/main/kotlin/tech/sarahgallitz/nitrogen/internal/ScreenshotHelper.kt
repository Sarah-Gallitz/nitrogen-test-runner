package tech.sarahgallitz.nitrogen.internal

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziTaskType
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage
import com.jraska.falcon.Falcon
import java.io.File
import java.io.FileOutputStream
import tech.sarahgallitz.nitrogen.environment.TestEnvironment

internal object ScreenshotHelper {
    private fun saveScreenshot(file: File, image: Bitmap) {
        file.getParentFile()?.mkdirs()
        file.createNewFile()

        val out = FileOutputStream(file)

        image.compress(Bitmap.CompressFormat.PNG, 100, out)

        out.flush()
        out.close()
    }

    @OptIn(ExperimentalRoborazziApi::class)
    fun takeScreenshot(activity: Activity, fileName: String) {
        val directory = if (TestEnvironment.isJvm) {
            TestEnvironment.configuration.getJvmScreenshotsDir()
        } else {
            TestEnvironment.configuration.getOnDeviceScreenshotsDir()
        }

        val file = File("$directory/$fileName.png")
        Log.d("NITROGEN", "Saving file to ${file.path}")

        if (TestEnvironment.isJvm) {
            captureScreenRoboImage(file, RoborazziOptions(taskType = RoborazziTaskType.Record))
        } else {
            val bitmap = Falcon.takeScreenshotBitmap(activity)
            saveScreenshot(file, bitmap)
        }
    }

    fun takeScreenshot(node: SemanticsNodeInteraction, fileName: String) {
        val directory = if (TestEnvironment.isJvm) {
            TestEnvironment.configuration.getJvmScreenshotsDir()
        } else {
            TestEnvironment.configuration.getOnDeviceScreenshotsDir()
        }

        val file = File("$directory/$fileName.png")
        Log.d("NITROGEN", "Saving file to ${file.path}")

        if (TestEnvironment.isJvm) {
            node.captureRoboImage(file, RoborazziOptions(taskType = RoborazziTaskType.Record))
        } else {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                node.captureToImage().asAndroidBitmap()
            } else {
                throw RuntimeException("Nitrogen takeScreenshot onNode is only available for Android 8.0 and above")
            }
            saveScreenshot(file, bitmap)
        }
    }
}

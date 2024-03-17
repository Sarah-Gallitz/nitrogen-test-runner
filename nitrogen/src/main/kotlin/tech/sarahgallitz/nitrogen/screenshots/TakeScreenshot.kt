package tech.sarahgallitz.nitrogen.screenshots

import android.app.Activity
import androidx.compose.ui.test.SemanticsNodeInteraction
import tech.sarahgallitz.nitrogen.internal.ScreenshotHelper

fun Activity.takeScreenshot(fileName: String) {
    ScreenshotHelper.takeScreenshot(this, fileName)
}


fun SemanticsNodeInteraction.takeScreenshot(fileName: String) {
    ScreenshotHelper.takeScreenshot(this, fileName)
}
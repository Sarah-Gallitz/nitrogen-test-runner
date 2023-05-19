package tech.sarahgallitz.nitrogen.environment

import android.os.Build

object TestEnvironment {
    val isRobolectric: Boolean
        get() = Build.FINGERPRINT == "robolectric"
}

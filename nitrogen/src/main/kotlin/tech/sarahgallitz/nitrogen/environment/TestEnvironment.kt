package tech.sarahgallitz.nitrogen.environment

import android.os.Build

object TestEnvironment {
    val isJvm: Boolean
        get() = Build.FINGERPRINT == "robolectric"

    var configuration = NitrogenConfiguration()
}

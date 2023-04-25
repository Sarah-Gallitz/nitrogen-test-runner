package au.sgallitz.nitrogen

import android.os.Build

object TestEnvironment {
    val isRobolectric: Boolean
        get() = Build.FINGERPRINT == "robolectric"
}

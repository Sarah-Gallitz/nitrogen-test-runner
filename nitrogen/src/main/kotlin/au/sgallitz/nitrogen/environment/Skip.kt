package au.sgallitz.nitrogen.environment

import org.junit.Assume.assumeFalse
import org.junit.Assume.assumeTrue

fun skipOnJVM() = assumeFalse(TestEnvironment.isRobolectric)
fun skipOnDevice() = assumeTrue(TestEnvironment.isRobolectric)

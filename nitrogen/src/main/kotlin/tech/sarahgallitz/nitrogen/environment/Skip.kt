package tech.sarahgallitz.nitrogen.environment

import org.junit.Assume.assumeFalse
import org.junit.Assume.assumeTrue

fun skipOnJVM() = assumeFalse(TestEnvironment.isJvm)
fun skipOnDevice() = assumeTrue(TestEnvironment.isJvm)

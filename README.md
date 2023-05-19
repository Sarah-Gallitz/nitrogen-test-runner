# Nitrogen Test Runner
A gradle plugin and testing library that lets you quickly and easily switch between JVM and Emulator/Device for running Android Instrumentation tests.

**:construction: This project is in alpha, use with caution as the API is still under active development and may change**

## Getting Started

Create a new module to hold your Instrumentation tests

Apply the nitrogen plugin and the kotlin android plugin:
``` kts
plugins {
    id("tech.sarahgallitz.nitrogen-plugin") version "0.0.1"
    id("org.jetbrains.kotlin.android")
}
```

Set the test module applicationId:
``` kts
android {
    defaultConfig {
        testApplicationId = "demo.uiTests"
    }
}
```

Add a dependency on the android-application module which the tests should run against, and add the nitrogen test library:
``` kts
dependencies {
    implementation(project(":demo:app"))
    implementation("tech.sarahgallitz:nitrogen:0.0.1")
}
```
_Don't forget to add androidx and JUnit dependencies as appopriate to your project. Nitrogen works with the AndroidX testing framework._

Add a `nitrogen.config` file at the top level of your project, or at the top level of the instrumentation module. The `nitrogen.config` file should define the following properties:

``` txt
isRunningOnJVM = true
targetProjectPath = :demo:app
```

_Note: When changing the value of `isRunningOnJVM` you should run a gradle sync afterwards._

Add your first test:
``` kt
@RunWith(NitrogenTestRunner::class)
class ExampleNitrogenTest {
    @Test
    fun exampleTest() {
        assertTrue(1 == 1)
    }

    @Test
    fun exampleJVMOnlyTest() {
        skipOnDevice()

        assertTrue(1 == 1)
    }

    @Test
    fun exampleDeviceOnlyTest() {
        skipOnJVM()
        assertTrue(1 == 1)
    }
}
```

## The Tech
Nitrogen is built on top of:
- AndroidX
- Robolectric

At gradle configuration time it applies either the `android-library` plugin (for JVM target) or the `android-test` plugin (for device target).

## Known Issues
#### Test Android Manifest
Robolectric requires activities to be added to the AndroidManifest of the test module. Currently the nitrogen plugin attempts to autogenerate the AndroidManifest for you, but this has some issues:
- any existing manifest will be overwritten
- the manifest generation is pretty dodgy and may not work for all applications

#### Tests that navigate over multiple screens
It is currently not possible to test real activity to activity navigation when targeting the JVM, please mark these tests with `skipOnJVM()`.

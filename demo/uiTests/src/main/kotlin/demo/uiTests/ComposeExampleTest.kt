package demo.uiTests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import demo.app.MainActivity
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import tech.sarahgallitz.nitrogen.environment.skipOnDevice
import tech.sarahgallitz.nitrogen.environment.skipOnJVM
import tech.sarahgallitz.nitrogen.runner.NitrogenTestRunner

@RunWith(NitrogenTestRunner::class)
class ComposeExampleTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun givenADemoApp_whenAppIsLaunched_thenHelloWorldIsShown() {
        composeTestRule
            .onNodeWithText("Hello World")
            .assertIsDisplayed()
    }

    @Test
    fun givenADemoApp_whenAppIsLaunched_thenHelloWorldIsShown_JVMOnly() {
        skipOnDevice()

        composeTestRule
            .onNodeWithText("Hello World")
            .assertIsDisplayed()
    }

    @Test
    fun givenADemoApp_whenAppIsLaunched_thenHelloWorldIsShown_DeviceOnly() {
        skipOnJVM()

        composeTestRule
            .onNodeWithText("Hello World")
            .assertIsDisplayed()
    }
}

package au.sgallitz.nitrogen.demo.uiTests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import au.sgallitz.nitrogen.demo.MainActivity
import au.sgallitz.nitrogen.runner.NitrogenTestRunner
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

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
}

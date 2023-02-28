package au.sgallitz.nitrogen.demo.uiTests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import au.sgallitz.nitrogen.demo.MainActivity
import org.junit.Rule
import kotlin.test.Test

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

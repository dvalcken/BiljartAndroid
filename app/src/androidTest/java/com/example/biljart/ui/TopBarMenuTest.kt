package com.example.biljart.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.biljart.Destinations
import com.example.biljart.R
import com.example.biljart.ui.theme.BilliardTheme
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopBarMenuTest {

    // Helper function to get a string from the resources,
    // because the normal way to get resources in a test doesn't work in a Compose test
    private fun getResourceString(@StringRes resId: Int): String {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.getString(resId)
    }

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController // lateinit is used to initialize the variable later

    // Mutable state to track theme state
    private var isDarkTheme = mutableStateOf(false)

    @Before
    fun initializeApp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BilliardTheme(darkTheme = isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BilliardApp(
                        toggleTheme = { isDarkTheme.value = !isDarkTheme.value },
                        navController = navController,
                    )
                }
            }
        }
    }

    @Test
    fun click_on_topbar_more_icon_shows_toggle_theme_item_and_toggles_isDarkTheme() {
        // Click on the more icon to show the dropdown menu
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.menu))
            .performClick()

        // Verify the toggle theme item is shown
        composeTestRule
            .onNodeWithText(getResourceString(R.string.toggle_theme))
            .assertExists()
            .assertIsDisplayed()

        // Click the toggle theme item
        composeTestRule
            .onNodeWithText(getResourceString(R.string.toggle_theme))
            .performClick()

        // Wait for the theme change to take effect
        composeTestRule.waitForIdle()

        // Assert that the isDarkTheme variable has been toggled
        assert(isDarkTheme.value) { "The theme should have been toggled to dark." }
    }

    @Test
    fun click_on_topbar_more_icon_shows_about_item_and_navigates_to_aboutscreen() {
        // Click on the more icon to show the dropdown menu
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.menu))
            .performClick()

        // Verify the About item is shown and click it
        composeTestRule
            .onNodeWithText(getResourceString(R.string.about_title))
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        // Verify that the About screen is shown (via the navcontroller current destination)
        assertEquals(Destinations.About.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun test_fab_visibility_on_about_screen() {
        // Directly navigate to the About screen
        composeTestRule.runOnUiThread {
            navController.navigate(Destinations.About.route)
        }

        // Wait for the content to load
        composeTestRule.waitForIdle()

        // Verify the FAB is shown
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.email_the_developer))
            .assertExists()
            .assertIsDisplayed()
    }
}

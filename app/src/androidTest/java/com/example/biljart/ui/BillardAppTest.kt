package com.example.biljart.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
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
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BillardAppTest {

    // Helper function to get a string from the resources,
    // because the normal way to get resources in a test doesn't work in a Compose test
    // Les 5 11'20"
    private fun getResourceString(@StringRes resId: Int): String {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.getString(resId)
    }

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController // lateinit is used to initialize the variable later

    @Before
    fun initializeApp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BilliardApp(toggleTheme = { }, navController)
        }
    }

    // test if startscreen shows the logo
    @Test
    fun test_startscreen_shows_logo() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.logo))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun test_startscreen_shows_home_button() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_home))
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    // Moved to top bar dropdown menu
//    @Test
//    fun test_startscreen_shows_about_button() {
//        composeTestRule
//            .onNodeWithContentDescription(getResourceString(R.string.bottombar_about))
//            .assertExists()
//            .assertIsDisplayed()
//            .assertIsEnabled()
//    }

    @Test
    fun test_startscreen_shows_ranking_button() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_ranking))
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun test_startscreen_shows_playingdays_button() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_playingdays))
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun test_app_shows_startscreen() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_title))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun click_on_RankingIcon_navigates_to_RankingOverview_page() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_ranking))
            .performClick()
//        composeTestRule // this works, but is not the best way to test. See next lines
//            .onNodeWithText(getResourceString(R.string.ranking_title))
//            .assertIsDisplayed()
        assertEquals(Destinations.Ranking.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_topbar_more_icon_shows_toggle_theme_item() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.menu))
            .performClick()

        composeTestRule
            .onNodeWithText(getResourceString(R.string.toggle_theme))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun click_on_topbar_more_icon_shows_about_item_and_navigates_to_aboutscreen() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.menu))
            .performClick()

        composeTestRule
            .onNodeWithText(getResourceString(R.string.about_title))
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        assertEquals(Destinations.About.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_PlayingdayIcon_navigates_to_Playingdays_page() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_playingdays))
            .performClick()
        assertEquals(Destinations.Playingdays.route, navController.currentBackStackEntry?.destination?.route)
    }

    // Moved to top bar dropdown menu
//    @Test
//    fun test_fab_visibility_and_functionality() {
//        composeTestRule
//            .onNodeWithContentDescription(getResourceString(R.string.bottombar_about))
//            .performClick()
//
//        // Check if FAB is displayed -> temporarily test, component will be adapted
//        composeTestRule
//            .onNodeWithContentDescription("Email the developer")
//            .assertExists()
//            .assertIsDisplayed()
//    }

    @Test
    fun test_back_navigation() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_ranking))
            .performClick()

        // Verify navigation to Ranking
        assertEquals(Destinations.Ranking.route, navController.currentBackStackEntry?.destination?.route)

        // Simulate back button click
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.back))
            .performClick()

        // Verify navigation back to home
        assertEquals(Destinations.Home.route, navController.currentBackStackEntry?.destination?.route)
    }
}

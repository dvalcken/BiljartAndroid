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
import androidx.test.platform.app.InstrumentationRegistry
import com.example.biljart.Destinations
import com.example.biljart.R
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BillardAppTest {

    // Helper function to get a string from the resources,
    // because the normal way to get resources in a test doesn't work in a Compose test
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

    @Test
    fun test_startscreen_shows_about_button() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_about))
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

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

//    Deze is vervangen door test hieronder met getResourceString
//    @Test
//    fun test_app_shows_startscreen() {
//        composeTestRule
//            .onNodeWithText("Home")
//            .assertExists()
//            .assertIsDisplayed()
//    }

    @Test
    fun test_app_shows_startscreen() {
        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_title))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun click_on_about_navigates_to_RankingOverview_page() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_ranking))
            .performClick()
//        composeTestRule // this works, but is not the best way to test. See next lines
//            .onNodeWithText(getResourceString(R.string.ranking_title))
//            .assertIsDisplayed()
        assertEquals(Destinations.Ranking.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_about_navigates_to_About_page() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_about))
            .performClick()
        assertEquals(Destinations.About.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_about_navigates_to_PlayingDays_page() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_playingdays))
            .performClick()
        assertEquals(Destinations.Playingdays.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun test_fab_visibility_and_functionality() {
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.bottombar_about))
            .performClick()

        // Check if FAB is displayed -> temporarily test, component will be adapted
        composeTestRule
            .onNodeWithContentDescription("Email the developer")
            .assertExists()
            .assertIsDisplayed()
    }

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

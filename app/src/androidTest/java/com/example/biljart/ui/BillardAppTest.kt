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
            .onNodeWithContentDescription("Logo")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun test_startscreen_shows_home_button() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar Home")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun test_startscreen_shows_about_button() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar About")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun test_startscreen_shows_ranking_button() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar Ranking")
            .assertExists()
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun test_startscreen_shows_playingdays_button() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar Playingdays")
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

    private fun getResourceString(@StringRes key: Int): String {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(key)
    }

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
            .onNodeWithContentDescription("Bottombar Ranking")
            .performClick()
//        composeTestRule // this works, but is not the best way to test. See next lines
//            .onNodeWithText(getResourceString(R.string.ranking_title))
//            .assertIsDisplayed()
        assertEquals(Destinations.Ranking.name, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_about_navigates_to_About_page() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar About")
            .performClick()
//        composeTestRule // this works, but is not the best way to test. See next lines
//            .onNodeWithText(getResourceString(R.string.about_title))
//            .assertIsDisplayed()
        assertEquals(Destinations.About.name, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun click_on_about_navigates_to_PlayingDays_page() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar Playingdays")
            .performClick()
//        composeTestRule // this works, but is not the best way to test. See next lines
//            .onNodeWithText(getResourceString(R.string.playing_days_title))
//            .assertIsDisplayed()
        assertEquals(Destinations.PlayingDays.name, navController.currentBackStackEntry?.destination?.route)
    }
}

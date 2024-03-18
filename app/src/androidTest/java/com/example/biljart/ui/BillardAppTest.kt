package com.example.biljart.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.biljart.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BillardAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initializeApp() {
        composeTestRule.setContent {
            BilliardApp(toggleTheme = { })
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
        composeTestRule
            .onNodeWithText(getResourceString(R.string.ranking_title))
            .assertIsDisplayed()
    }

    @Test
    fun click_on_about_navigates_to_About_page() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar About")
            .performClick()
        composeTestRule
            .onNodeWithText(getResourceString(R.string.about_title))
            .assertIsDisplayed()
    }

    @Test
    fun click_on_about_navigates_to_PlayingDays_page() {
        composeTestRule
            .onNodeWithContentDescription("Bottombar Playingdays")
            .performClick()
        composeTestRule
            .onNodeWithText(getResourceString(R.string.playing_days_title))
            .assertIsDisplayed()
    }
}

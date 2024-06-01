@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui.Match

import android.util.Log
import com.example.biljart.data.MatchRepository
import com.example.biljart.ui.matcheditcomponents.EditMatchScoreViewModel
import com.example.biljart.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditMatchScoreViewModelTest {
    private val matchRepository = mockk<MatchRepository>(relaxed = true)
    private val matchId = 1 // Example match ID
    private val player1FramesWon = 6 // Example number of frames won by player 1
    private val player2FramesWon = 2 // Example number of frames won by player 2

    private lateinit var viewModel: EditMatchScoreViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.w(any(), any(), any()) } returns 0

        viewModel = EditMatchScoreViewModel(matchRepository, matchId)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain()
    }

    @Test
    fun `test updateScores updates the match score successfully`() = runTest {
        coEvery { matchRepository.updateMatchScores(matchId, player1FramesWon, player2FramesWon) } just Runs

        viewModel.updateScores(player1FramesWon, player2FramesWon)

        // Simulate the delay for the coroutine to complete
        advanceUntilIdle()

        // Verify that the matchRepository's updateMatchScores method was called with the correct parameters
        coVerify(exactly = 1) { matchRepository.updateMatchScores(matchId, player1FramesWon, player2FramesWon) }
    }
}

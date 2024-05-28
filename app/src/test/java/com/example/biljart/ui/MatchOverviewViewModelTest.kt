@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.MatchRepository
import com.example.biljart.fake.FakeMatchDataSource
import com.example.biljart.fake.asDomainObject
import com.example.biljart.ui.matchoverviewcomponents.MatchOverviewApiState
import com.example.biljart.ui.matchoverviewcomponents.MatchOverviewViewModel
import com.example.biljart.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MatchOverviewViewModelTest {
    private val matchRepository = mockk<MatchRepository>(relaxed = true)

    private val playingdayId = FakeMatchDataSource.matches.first().playingday.playingday_id

    private lateinit var viewModel: MatchOverviewViewModel

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

        every { matchRepository.getMatchesByPlayingDay(playingdayId) } returns flow {
            delay(200)
            emit(FakeMatchDataSource.matches.map { it.asDomainObject() })
        }

        coEvery { matchRepository.refreshMatches(playingdayId) } just Runs

        viewModel = MatchOverviewViewModel(matchRepository, playingdayId)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain()
    }

    @Test
    fun `test if the viewmodel initializes and fetches match data correctly`() = runTest {
        val job = launch {
            viewModel.matchListAsState.collect { }
        }

        advanceUntilIdle()

        val matches = viewModel.matchListAsState.value
        val numberOfFakeMatches = FakeMatchDataSource.matches.size
        assertEquals(numberOfFakeMatches, matches.size, "The size of matches should be $numberOfFakeMatches but was ${matches.size}")
        assertEquals(FakeMatchDataSource.matches.first().matchId, matches.first().matchId, "The match ID should match")

        job.cancel()
    }

    @Test
    fun `test for empty list when no matches are available`() = runTest {
        every { matchRepository.getMatchesByPlayingDay(playingdayId) } returns flowOf(emptyList())

        viewModel = MatchOverviewViewModel(matchRepository, playingdayId) // Reinitialize the viewmodel
        val matches = viewModel.matchListAsState.value
        assertTrue(matches.isEmpty(), "Match list should be empty")
    }

    @Test
    fun `test for error state when fetching data fails`() = runTest {
        every { matchRepository.getMatchesByPlayingDay(playingdayId) } throws Exception("Failed to fetch matches")

        val state = viewModel.matchApiState
        assertEquals(MatchOverviewApiState.Error, state, "ViewModel should reflect error state")
    }
}

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.PlayingdayRepository
import com.example.biljart.fake.FakePlayingdayDataSource
import com.example.biljart.network.asDomainObjects
import com.example.biljart.ui.playingdaycomponents.PlayingdayApiState
import com.example.biljart.ui.playingdaycomponents.PlayingdayOverviewViewModel
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

class PlayingdayOverviewViewModelTest {

    // ! For explanation of setup and tests, see the comments in the RankingOverviewViewModelTest.kt file !

    private val playingdayRepository = mockk<PlayingdayRepository>(relaxed = true)

    private lateinit var viewModel: PlayingdayOverviewViewModel

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

        every { playingdayRepository.getAllPlayingdays() } returns flow {
            delay(200)
            emit(FakePlayingdayDataSource.playingdays.asDomainObjects())
        }

        coEvery { playingdayRepository.refreshPlayingdays() } just Runs

        viewModel = PlayingdayOverviewViewModel(playingdayRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain()
    }

    @Test
    fun `test if the viewmodel initializes and fetches playingday data correctly`() = runTest {
        val job = launch {
            viewModel.playingdayListAsState.collect { }
        }

        advanceUntilIdle()

        val playingdays = viewModel.playingdayListAsState.value
        val numberOfFakePlayingdays = FakePlayingdayDataSource.playingdays.size
        assertEquals(numberOfFakePlayingdays, playingdays.size, "The size of playingdays should be $numberOfFakePlayingdays but was ${playingdays.size}")
        assertEquals(FakePlayingdayDataSource.playingdays.first().date, playingdays.first().date, "The playingday's date should match")

        job.cancel()
    }

    @Test
    fun `test for empty list when no playingdays are available`() = runTest {
        every { playingdayRepository.getAllPlayingdays() } returns flowOf(emptyList())

        viewModel = PlayingdayOverviewViewModel(playingdayRepository) // Reinitialize the viewmodel
        val playingdays = viewModel.playingdayListAsState.value
        assertTrue(playingdays.isEmpty(), "Playingday list should be empty")
    }

    @Test
    fun `test for error state when fetching data fails`() = runTest {
        every { playingdayRepository.getAllPlayingdays() } throws Exception("Failed to fetch playingdays")

        val state = viewModel.playingdayApiState
        assertEquals(PlayingdayApiState.Error, state, "ViewModel should reflect error state")
    }
}

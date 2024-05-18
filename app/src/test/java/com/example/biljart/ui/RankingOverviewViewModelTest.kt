@file:OptIn(
    ExperimentalCoroutinesApi::class,
)

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.PlayerRepository
import com.example.biljart.fake.FakePlayerDataSource
import com.example.biljart.network.asDomainObjects
import com.example.biljart.ui.rankingcomponents.RankingOverviewViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertEquals

class RankingOverviewViewModelTest { // Lesson 8  47' until end

    // See https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/
    // for more information on this test setup

    // I'm using MockK for mocking objects, see https://mockk.io/, instead of depending on a FakePlayerRepository
    // (mainly because I don't want to maintain a FakePlayerRepository)
    // Furthermore, I needed a mocking solution for the Log class (several Log calls in the ViewModel) -> see setUp() method
    private val playerRepository = mockk<PlayerRepository>(relaxed = true) // relaxed = true to avoid exceptions when calling unimplemented methods

    private lateinit var viewModel: RankingOverviewViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        MockKAnnotations.init(this, relaxUnitFun = true) // Initialize MockK annotations
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.w(any(), any(), any()) } returns 0

        every { playerRepository.getAllPlayers() } returns flowOf(
            FakePlayerDataSource.players.asDomainObjects(),
        )
        viewModel = RankingOverviewViewModel(playerRepository)
    }

    @After
    fun tearDown() {
        unmockkAll() // clear all mocks after tests

        Dispatchers.resetMain()
    }

    @Test
    fun `test if the viewmodel initializes and fetches player data correctly`() = runTest {
        val job = launch {
            viewModel.rankingListAsState.collect { } // Collect and do nothing, just trigger the flow
        }

        advanceUntilIdle() // Proceed all coroutines until they are idle (all suspend functions are completed
        // Without this, I had the issue that the list was empty (size 0) because the flow didn't have time to emit

        // Check the values after the flow has had time to emit
        val players = viewModel.rankingListAsState.value
        val numberOfFakePlayers = FakePlayerDataSource.players.size
        assertEquals(numberOfFakePlayers, players.size, "The size of players should be $numberOfFakePlayers but was ${players.size}")
        assertEquals(FakePlayerDataSource.players.first().name, players.first().name, "The player's name should match")

        job.cancel() // Cancel the job to clean up resources
    }

    // This was the original test, but it didn't work because the flow didn't have time to emit, so actual size of playerslist was 0
//    @Test
//    fun `test  if the viewmodel initializes and fetches player data correctly`() = runTest {
//        // No need to manually launch a coroutine or manage job
//        val players = viewModel.rankingListAsState.value
//        val numberOfFakePlayers = FakePlayerDataSource.players.size
//        assertEquals(numberOfFakePlayers, players.size, "The size of players should be $numberOfFakePlayers")
//        assertEquals(FakePlayerDataSource.players.first().name, players.first().name, "The player's name should match")
//    }
}

class CoroutineTestRule(
    private val testDispatcher: TestDispatcher,
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

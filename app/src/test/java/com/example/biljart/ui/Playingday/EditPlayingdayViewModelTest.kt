@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui.Playingday

import android.util.Log
import com.example.biljart.data.PlayingdayRepository
import com.example.biljart.ui.playingdayeditcomponens.EditPlayingdayViewModel
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

class EditPlayingdayViewModelTest {

    private val playingdayRepository = mockk<PlayingdayRepository>(relaxed = true)
    private val playingdayId = 1 // Example playingday ID
    private val isFinished = true // Example status

    private lateinit var viewModel: EditPlayingdayViewModel

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

        viewModel = EditPlayingdayViewModel(playingdayRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()

        Dispatchers.resetMain()
    }

    @Test
    fun `test updateStatus updates the playingday status successfully`() = runTest {
        coEvery { playingdayRepository.updatePlayingdayStatus(playingdayId, isFinished) } just Runs

        viewModel.updateStatus(playingdayId, isFinished)

        // Simulate the delay for the coroutine to complete
        advanceUntilIdle()

        // Verify that the playingdayRepository's updatePlayingdayStatus method was called with the correct parameters
        coVerify(exactly = 1) { playingdayRepository.updatePlayingdayStatus(playingdayId, isFinished) }
    }
}

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.CashingPlayingdayRepository
import com.example.biljart.data.database.PlayingdayDao
import com.example.biljart.data.database.asDbPlayingday
import com.example.biljart.fake.FakePlayingdayDataSource
import com.example.biljart.fake.asDomainObject
import com.example.biljart.network.PlayingdayApiService
import com.example.biljart.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class PlayingdayRepositoryTest {
    private lateinit var repository: CashingPlayingdayRepository

    // Mock the API service and DAO that the repository depends on
    private val mockApiService: PlayingdayApiService = mockk(relaxed = true)
    private val mockPlayingdayDao: PlayingdayDao = mockk(relaxed = true)

    // Prepare the data as domain objects, API objects, and DB objects
    private val playingdays = FakePlayingdayDataSource.playingdays.map { it.asDomainObject() }
    private val apiPlayingdays = FakePlayingdayDataSource.playingdays
    private val dbPlayingdays = playingdays.map { it.asDbPlayingday() }

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Prepare the mocked responses
        MockKAnnotations.init(this, relaxUnitFun = true) // Initialize MockK annotations
        mockkStatic(Log::class) // Mock all static functions of Log class that are called in the repository
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.w(any(), any(), any()) } returns 0

        every { mockPlayingdayDao.getAllPlayingdays() } returns flowOf(dbPlayingdays)
        // coEvery instead of every for suspending functions !
        coEvery { mockPlayingdayDao.insert(any()) } returns Unit
        // IMPORTANT, this is mocking the underlying API call of the extension function
        // (getPlayingdaysAsFlow is an extension function on PlayingdayApiService)
        coEvery { mockApiService.getAllPlayingdays() } returns apiPlayingdays

        // Extra mock for the updatePlayingdayStatus function compared to the PlayerRepositoryTest
        // This uses a Response object instead of a Unit object from the API service (Retrofit)
        coEvery { mockApiService.updatePlayingdayStatus(any(), any()) } returns Response.success(Unit)

        // Initialize the repository with mocked API service and DAO
        repository = CashingPlayingdayRepository(mockApiService, mockPlayingdayDao)
    }

    @After
    fun tearDown() {
        unmockkAll() // clear all mocks after tests

        Dispatchers.resetMain()
    }

    @Test
    fun `getAllPlayingdays should emit playingdays from DAO`() = runTest {
        val result = repository.getAllPlayingdays().toList().flatten() // From List<List<Playingday>> to List<Playingday>
        assertEquals(playingdays.size, result.size)
        assertEquals(playingdays.first(), result.first())
    }

    @Test
    fun `refreshPlayingdays should fetch from API and insert into DAO`() = runTest {
        repository.refreshPlayingdays()
        // Verify that the API service was called only once
        coVerify(exactly = 1) { mockApiService.getAllPlayingdays() }
        // Verify that the DAO was called for each playingday
        coVerify(exactly = playingdays.size) { mockPlayingdayDao.insert(any()) }
    }

    @Test
    fun `insert should add a playingday to the DAO`() = runTest {
        val newId = playingdays.maxOf { it.playingdayId } + 1 // Create a new ID that is higher than all existing IDs
        val newPlayingday = playingdays.first().copy(playingdayId = newId) // Create a new playingday with a different ID
        repository.insert(newPlayingday)

        // Verify that the DAO was called with the new playingday
        coVerify(exactly = 1) { mockPlayingdayDao.insert(newPlayingday.asDbPlayingday()) }
    }

    @Test
    fun `updatePlayingdayStatus should update status in API and DAO`() = runTest {
        val testPlayingday = FakePlayingdayDataSource.playingdays.first()
        val playingdayId = testPlayingday.playingday_id
        val isFinished = !testPlayingday.is_finished // Toggle the is_finished status

        repository.updatePlayingdayStatus(playingdayId, isFinished)

        // Verify that the API service was called to update the playingday status
        coVerify(exactly = 1) { mockApiService.updatePlayingdayStatus(playingdayId, isFinished) }
        // Verify that the DAO was called to update the playingday status
        coVerify(exactly = 1) { mockPlayingdayDao.updatePlayingdayStatus(playingdayId, isFinished) }
    }
}

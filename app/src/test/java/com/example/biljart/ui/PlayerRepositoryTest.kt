@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.CashingPlayerRepository
import com.example.biljart.data.database.PlayerDao
import com.example.biljart.data.database.asDbPlayer
import com.example.biljart.fake.FakePlayerDataSource
import com.example.biljart.fake.asDomainObject
import com.example.biljart.network.PlayerApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

class PlayerRepositoryTest { // Lesson 8  47' until end

    private lateinit var repository: CashingPlayerRepository

    // Mock the API service and DAO that the repository depends on
    private val mockApiService: PlayerApiService = mockk(relaxed = true)
    private val mockPlayerDao: PlayerDao = mockk(relaxed = true)

    // Prepare the data as domain objects, API objects and DB objects
    private val players = FakePlayerDataSource.players.map { it.asDomainObject() }
    private val apiPlayers = FakePlayerDataSource.players
    private val dbPlayers = players.map { it.asDbPlayer() }

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

        every { mockPlayerDao.getAllPlayers() } returns flowOf(dbPlayers)
        every { mockPlayerDao.getById(any()) } returns flowOf(dbPlayers.first())
        // coEvery instead of every for suspending functions !
        coEvery { mockPlayerDao.insert(any()) } returns Unit
        // IMPORTANT, this is mocking the underlying API call of the extension function
        // (getPlayerssAsFlow is an extension function on PlayerApiService)
        coEvery { mockApiService.getAllRanks() } returns apiPlayers

        // Initialize the repository with mocked API service and DAO
        repository = CashingPlayerRepository(mockApiService, mockPlayerDao)
    }

    @After
    fun tearDown() {
        unmockkAll() // clear all mocks after tests

        Dispatchers.resetMain()
    }

    @Test
    fun `getAllPlayers should emit players from DAO`() = runTest {
        val result = repository.getAllPlayers().toList().flatten() // From List<List<Player>> to List<Player>
        assertEquals(players.size, result.size)
        assertEquals(players.first(), result.first())
    }

    @Test
    fun `refreshPlayers should fetch from API and insert into DAO`() = runTest {
        repository.refreshPlayers()
        // Verify that the API service was called only once
        coVerify(exactly = 1) { mockApiService.getAllRanks() }
        // Verify that the DAO was called for each player
        coVerify(exactly = players.size) { mockPlayerDao.insert(any()) }
    }

    @Test
    fun `getById should fetch player by id`() = runTest {
        val playerId = FakePlayerDataSource.players.first().player_id
        coEvery { mockPlayerDao.getById(playerId) } returns flowOf(players.first().asDbPlayer())

        val result = repository.getById(playerId).toList() // toList() is needed to collect the flow

        // Only one player should be emitted
        assertEquals(1, result.size)
        // The emitted player should match the player with the given ID
        assertEquals(players.first { it.playerId == playerId }, result.first())
    }

    @Test
    fun `insert should add a player to the DAO`() = runTest {
        val newPlayer = FakePlayerDataSource.players.first().asDomainObject()
        repository.insert(newPlayer)

        // Verify that the DAO was called with the new player
        coVerify { mockPlayerDao.insert(newPlayer.asDbPlayer()) }
    }
}

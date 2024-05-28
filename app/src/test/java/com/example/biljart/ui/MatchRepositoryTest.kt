@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import android.util.Log
import com.example.biljart.data.CashingMatchRepository
import com.example.biljart.data.PlayerRepository
import com.example.biljart.data.database.MatchDao
import com.example.biljart.data.database.PlayingdayDao
import com.example.biljart.data.database.asDbMatch
import com.example.biljart.data.database.asDbPlayingday
import com.example.biljart.fake.FakeMatchDataSource
import com.example.biljart.fake.FakePlayerDataSource
import com.example.biljart.fake.FakePlayingdayDataSource
import com.example.biljart.fake.asDomainObject
import com.example.biljart.network.MatchApiService
import com.example.biljart.network.asDbMatch
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
import retrofit2.Response

class MatchRepositoryTest {
    private lateinit var repository: CashingMatchRepository

    // Mock the dependencies that the repository depends on
    private val mockMatchDao: MatchDao = mockk(relaxed = true)
    private val mockPlayingdayDao: PlayingdayDao = mockk(relaxed = true)
    private val mockPlayerRepository: PlayerRepository = mockk(relaxed = true)
    private val mockApiService: MatchApiService = mockk(relaxed = true)

    // Prepare the data as domain objects and DB objects
    private val matches = FakeMatchDataSource.matches.map { it.asDomainObject() }
    private val dbMatches = matches.map { it.asDbMatch() }
    private val playingdays = FakePlayingdayDataSource.playingdays.map { it.asDomainObject() }
    private val players = FakePlayerDataSource.players.map { it.asDomainObject() }

    //  player1 and player2 in the test result may never be the same
    //  To ensure that the mocks for PlayerRepository.getById return the correct player for each ID,
    //  this is changed to map the player IDs to player objects.
    private val playerMap = players.associateBy { it.playerId }

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

        // Mocking DAO and API calls
        every { mockMatchDao.getMatchesByPlayingDay(any()) } returns flowOf(dbMatches)
        every { mockPlayingdayDao.getById(any()) } returns flowOf(playingdays.first().asDbPlayingday())

        // Mocking player repository calls
        coEvery { mockPlayerRepository.getById(any()) } answers {
            val playerId = arg<Int>(0)
            flowOf(playerMap[playerId]!!)
        }

        coEvery { mockPlayerRepository.insert(any()) } returns Unit
        coEvery { mockPlayerRepository.refreshPlayers() } returns Unit

        coEvery { mockApiService.updateMatchScores(any(), any(), any()) } returns Response.success(Unit)

        // IMPORTANT, this is mocking the underlying API call of the extension function of the API service
        coEvery { mockApiService.getMatchesByPlayingDay(any()) } returns FakeMatchDataSource.matches

        // Initialize the repository with mocked dependencies
        repository = CashingMatchRepository(mockMatchDao, mockPlayingdayDao, mockApiService, mockPlayerRepository)
    }

    @After
    fun tearDown() {
        unmockkAll() // clear all mocks after tests

        Dispatchers.resetMain()
    }

    @Test
    fun `getMatchesByPlayingDay should emit matches from DAO`() = runTest {
        val playingDayId = FakePlayingdayDataSource.playingdays.first().playingday_id
        val result = repository.getMatchesByPlayingDay(playingDayId).toList().flatten() // From List<List<Match>> to List<Match>
        assertEquals(matches.size, result.size)
        assertEquals(matches.first(), result.first())
    }

    @Test
    fun `insertMatch should add a match to the DAO`() = runTest {
        val newId = matches.maxOf { it.matchId } + 1 // Create a new ID that is higher than all existing IDs
        val newMatch = matches.first().copy(matchId = newId) // Create a new match with a different ID
        repository.insertMatch(newMatch)

        // Verify that the DAO was called with the new match
        coVerify(exactly = 1) { mockMatchDao.insertMatch(newMatch.asDbMatch()) }
    }

    @Test
    fun `updateMatchScores should update scores in API and DAO`() = runTest {
        val testMatch = FakeMatchDataSource.matches.first()
        val matchId = testMatch.matchId
        val player1FramesWon = 3
        val player2FramesWon = 2

        repository.updateMatchScores(matchId, player1FramesWon, player2FramesWon)

        // Verify that the API service was called to update the match scores
        coVerify(exactly = 1) { mockApiService.updateMatchScores(matchId, player1FramesWon, player2FramesWon) }
        // Verify that the DAO was called to update the match scores
        coVerify(exactly = 1) { mockMatchDao.updateMatchScores(matchId, player1FramesWon, player2FramesWon) }
    }

    @Test
    fun `refreshMatches should fetch from API and insert into DAO`() = runTest {
        val playingDayId = FakePlayingdayDataSource.playingdays.first().playingday_id

        repository.refreshMatches(playingDayId)

        // Verify that the playerRepository's refreshPlayers method was called
        coVerify(exactly = 1) { mockPlayerRepository.refreshPlayers() }

        // Verify that the API service was called to fetch matches
        coVerify(exactly = 1) { mockApiService.getMatchesByPlayingDay(playingDayId) }

        // Verify that the DAO's insertMatch method was called for each match
        coVerify(exactly = FakeMatchDataSource.matches.size) { mockMatchDao.insertMatch(any()) }
    }
}

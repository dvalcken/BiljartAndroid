@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import com.example.biljart.data.CashingPlayerRepository
import com.example.biljart.data.database.PlayerDao
import com.example.biljart.data.database.asDbPlayer
import com.example.biljart.fake.FakePlayerDataSource
import com.example.biljart.network.PlayerApiService
import com.example.biljart.network.asDomainObjects
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class SimplifiedPlayerRepositoryTest {

    private lateinit var repository: CashingPlayerRepository
    private val mockApiService: PlayerApiService = mockk(relaxed = true)
    private val mockPlayerDao: PlayerDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        every { mockPlayerDao.getAllPlayers() } returns flowOf(
            FakePlayerDataSource.players.asDomainObjects().map { it.asDbPlayer() },
        )

        // Initialize the repository with mocked API service and DAO
        repository = CashingPlayerRepository(mockApiService, mockPlayerDao)
    }

//    @After
//    fun tearDown() {
//        unmockkAll() // clear all mocks after tests
//
//        Dispatchers.resetMain()
//    }

    @Test
    fun `getAllPlayers should emit players from DAO`() = runTest {
        val players = repository.getAllPlayers().toList().flatten()
        assertEquals(FakePlayerDataSource.players.size, players.size)
        assertEquals(FakePlayerDataSource.players.first().name, players.first().name)
    }

    // TODO `refreshPlayers should fetch from API and insert into DAO`()
    // TODO `getById should return player from DAO`()
    // TODO `insert should insert player into DAO`()
}

package com.example.biljart.ui

import com.example.biljart.data.ApiRankingRepository
import com.example.biljart.fake.FakeDataSource
import com.example.biljart.fake.FakeRankingApiService
import com.example.biljart.network.asDomainObjects
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiRankingRepositoryTest {

    @Test
    fun apiRankingRepository_getRanking_verifyRanksList() = runTest {
        // suspend function, see https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/  'runTest'

        // Arrange
        val repository = ApiRankingRepository(FakeRankingApiService())
        // Act
        // Assert
        assertEquals(FakeDataSource.ranks.asDomainObjects(), repository.getRanking())
    }
}

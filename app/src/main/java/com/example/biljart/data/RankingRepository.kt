package com.example.biljart.data

import com.example.biljart.model.Rank
import com.example.biljart.network.RankingApiService
import com.example.biljart.network.asDomainObjects

interface RankingRepository {
    suspend fun getRanking(): List<Rank>
}

class ApiRankingRepository(
    private val rankingApiService: RankingApiService, // Lesson 8: 10'30" This is the dependency injection
) : RankingRepository {
    override suspend fun getRanking(): List<Rank> {
        return rankingApiService.getRank().asDomainObjects()
    }
}

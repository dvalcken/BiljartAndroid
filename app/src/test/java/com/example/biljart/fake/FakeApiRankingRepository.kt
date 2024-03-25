package com.example.biljart.fake

import com.example.biljart.data.RankingRepository
import com.example.biljart.model.Rank
import com.example.biljart.network.asDomainObjects

class FakeApiRankingRepository : RankingRepository {
    override suspend fun getRanking(): List<Rank> {
        return FakeDataSource.ranks.asDomainObjects()
    }
}

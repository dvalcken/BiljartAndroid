package com.example.biljart.fake

import com.example.biljart.network.ApiRank
import com.example.biljart.network.RankingApiService

class FakeRankingApiService : RankingApiService {
    override suspend fun getRank(): List<ApiRank> {
        return FakeDataSource.ranks
    }
}

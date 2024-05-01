package com.example.biljart.fake

import com.example.biljart.network.ApiPlayer
import com.example.biljart.network.PlayerApiService

class FakePlayerApiService : PlayerApiService {
    override suspend fun getAllRanks(): List<ApiPlayer> {
        return FakeDataSource.ranks
    }
}

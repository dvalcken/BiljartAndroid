package com.example.biljart.fake

import com.example.biljart.data.PlayerRepository
import com.example.biljart.model.Player
import com.example.biljart.network.asDomainObjects

class FakeApiPlayerRepository : PlayerRepository {
    override suspend fun getRanking(): List<Player> {
        return FakeDataSource.ranks.asDomainObjects()
    }
}

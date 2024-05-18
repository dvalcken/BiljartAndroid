package com.example.biljart.fake

import com.example.biljart.data.PlayerRepository
import com.example.biljart.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePlayerRepository : PlayerRepository {
    override fun getAllPlayers(): Flow<List<Player>> = flow {
        emit(
            FakePlayerDataSource.players.map { it.asDomainObject() },
        )
    }

    override fun getById(playerId: Int): Flow<Player> {
        throw NotImplementedError("FakePlayerRepository.getById() is not implemented yet.")
    }

    override suspend fun insert(player: Player) {
        // Not needed for testing
    }

    override suspend fun refreshPlayers() {
        // Not needed for testing because the data is hardcoded
    }
}

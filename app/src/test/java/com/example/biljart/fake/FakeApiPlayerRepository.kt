package com.example.biljart.fake

import com.example.biljart.data.PlayerRepository
import com.example.biljart.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeApiPlayerRepository : PlayerRepository {
//    override suspend fun getRanking(): List<Player> {
//        return FakePlayerDataSource.players.asDomainObjects()
//    }
    override fun getAllPlayers(): Flow<List<Player>> = flow { emit(FakePlayerDataSource.players.map { it.asDomainObject() }) }
    override fun getById(playerId: Int): Flow<Player> = flow { emit(FakePlayerDataSource.players.find { it.player_id == playerId }!!.asDomainObject()) }
    override suspend fun insert(player: Player) { /* TODO implement later if needed for tests */ }
    override suspend fun refreshPlayers() { /* TODO simulate refresh logic */ }
}

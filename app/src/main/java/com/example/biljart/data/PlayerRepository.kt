package com.example.biljart.data

import android.util.Log
import com.example.biljart.data.database.PlayerDao
import com.example.biljart.data.database.asDbPlayer
import com.example.biljart.data.database.asDomainObject
import com.example.biljart.data.database.asDomainObjects
import com.example.biljart.model.Player
import com.example.biljart.network.PlayerApiService
import com.example.biljart.network.asDomainObjects
import com.example.biljart.network.getPlayerssAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface PlayerRepository {
//    suspend fun getRanking(): List<Rank> // Les 9 45' commented because getAllRanks from the RankDao is used instead

    fun getAllPlayers(): Flow<List<Player>>

    fun getById(playerId: Int): Flow<Player> // Not needed for the app, but useful for other entities

    suspend fun insert(player: Player) // Not needed for the app, but useful for other entities

    suspend fun refreshPlayers() // Because API and RoomDb are behind this repository, this function is needed to refresh the data in RoomDb from the a
}

// class ApiRankingRepository(  // Les 9 47'30" commented because new class is created in the next snippet
//    private val rankingApiService: RankingApiService, // Lesson 8: 10'30" This is the dependency injection
// ) : RankingRepository {
//    override suspend fun getRanking(): List<Rank> {
//        return rankingApiService.getRank().asDomainObjects()
//    }
// }

class CashingPlayerRepository( // Les 9 47'30"
    private val playerApiService: PlayerApiService,
    private val playerDao: PlayerDao,
) : PlayerRepository {
    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers().map { it.asDomainObjects() }
            .onEach { // Les 9 58'40" To refresh the data in the database when it is empty
                if (it.isEmpty()) {
                    try {
                        refreshPlayers()
                    } catch (e: SocketTimeoutException) {
                        Log.e("CashingPlayerRepository getAllPlayers on clean start SocketTimeoutException", "Network timeout on clean start when getAllRanks: ${e.message}", e)
                        // throw e  // Error is not rethrown, RankingApiState.Error is set in the ViewModel, and user is informed with a ErrorMessageComponent
                    } catch (e: Exception) {
                        Log.w("CashingPlayerRepository getAllPlayers-method error", "CashingPlayerRepository getAllPlayers-method error: ${e.message}", e)
                        throw e
                    }
                }
            }
    }

    override fun getById(playerId: Int): Flow<Player> {
        try {
            return playerDao.getById(playerId).map { it.asDomainObject() }
        } catch (e: Exception) {
            Log.w("CashingPlayerRepository getById-method error", "CashingRankingRepository getById-method error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun insert(player: Player) {
        try {
            playerDao.insert(player.asDbPlayer())
        } catch (e: Exception) {
            Log.w("CashingPlayerRepository insert-method error", "CashingRankingRepository insert-method error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun refreshPlayers() {
        try {
            playerApiService.getPlayerssAsFlow().collect {
                for (rank in it.asDomainObjects()) {
                    Log.i("refreshPlayers", "Refreshing players: $rank")
                    playerDao.insert(rank.asDbPlayer())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("CashingPlayerRepository refreshPlayers SocketTimeoutException", "Network timeout when refreshing players: ${e.message}", e)
            throw e
        } catch (e: Exception) {
            Log.w("CashingPlayerRepository refreshPlayers-method error", "CashingPlayerRepository refreshPlayers-method error: ${e.message}", e)
            throw e
        }
    }
}

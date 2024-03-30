package com.example.biljart.data

import android.util.Log
import com.example.biljart.data.database.RankDao
import com.example.biljart.data.database.asDbRank
import com.example.biljart.data.database.asDomainObject
import com.example.biljart.data.database.asDomainObjects
import com.example.biljart.model.Rank
import com.example.biljart.network.RankingApiService
import com.example.biljart.network.asDomainObjects
import com.example.biljart.network.getRanksAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface RankingRepository {
//    suspend fun getRanking(): List<Rank> // Les 9 45' commented because getAllRanks from the RankDao is used instead

    fun getAllRanks(): Flow<List<Rank>>

    fun getById(playerId: Int): Flow<Rank> // Not needed for the app, but useful for other entities

    suspend fun insert(rank: Rank) // Not needed for the app, but useful for other entities

    suspend fun refreshRanking() // Because API and RoomDb are behind this repository, this function is needed to refresh the data in RoomDb from the a
}

// class ApiRankingRepository(  // Les 9 47'30" commented because new class is created in the next snippet
//    private val rankingApiService: RankingApiService, // Lesson 8: 10'30" This is the dependency injection
// ) : RankingRepository {
//    override suspend fun getRanking(): List<Rank> {
//        return rankingApiService.getRank().asDomainObjects()
//    }
// }

class CashingRankingRepository( // Les 9 47'30"
    private val rankingApiService: RankingApiService,
    private val rankDao: RankDao,
) : RankingRepository {
    override fun getAllRanks(): Flow<List<Rank>> {
        try {
            return rankDao.getAllRanks().map { it.asDomainObjects() }
                .onEach { // Les 9 58'40" To refresh the data in the database when it is empty
                    if (it.isEmpty()) {
                        refreshRanking()
                    }
                }
        } catch (e: Exception) {
            Log.w("CashingRankingRepository getAllRanks-method error", "CashingRankingRepository getAllRanks-method error: ${e.message}", e)
            throw e
        }
    }

    override fun getById(playerId: Int): Flow<Rank> {
        try {
            return rankDao.getById(playerId).map { it.asDomainObject() }
        } catch (e: Exception) {
            Log.w("CashingRankingRepository getById-method error", "CashingRankingRepository getById-method error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun insert(rank: Rank) {
        try {
            rankDao.insert(rank.asDbRank())
        } catch (e: Exception) {
            Log.w("CashingRankingRepository insert-method error", "CashingRankingRepository insert-method error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun refreshRanking() {
        try {
            rankingApiService.getRanksAsFlow().collect {
                for (rank in it.asDomainObjects()) {
                    Log.i("refreshRanking", "Refreshing ranking: $rank")
                    rankDao.insert(rank.asDbRank())
                }
            }
        } catch (e: Exception) {
            Log.w("CashingRankingRepository refreshRanking-method error", "CashingRankingRepository refreshRanking-method error: ${e.message}", e)
            throw e
        }
    }
}

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
        return rankDao.getAllRanks().map { it.asDomainObjects() }
            .onEach { // Les 9 58'40" To refresh the data in the database when it is empty
                if (it.isEmpty()) {
                    refreshRanking()
                }
            }
    }

    override fun getById(playerId: Int): Flow<Rank> {
        return rankDao.getById(playerId).map { it.asDomainObject() }
    }

    override suspend fun insert(rank: Rank) {
        rankDao.insert(rank.asDbRank())
    }

    override suspend fun refreshRanking() {
        rankingApiService.getRanksAsFlow().collect {
            for (rank in it.asDomainObjects()) {
                Log.i("TEST", "Refreshing ranking: $rank")
                rankDao.insert(rank.asDbRank())
            }
        }
    }
}

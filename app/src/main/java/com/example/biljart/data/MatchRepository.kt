package com.example.biljart.data

import android.util.Log
import com.example.biljart.data.database.MatchDao
import com.example.biljart.data.database.PlayingdayDao
import com.example.biljart.data.database.asDbMatch
import com.example.biljart.data.database.asDomainObject
import com.example.biljart.model.Match
import com.example.biljart.model.Player
import com.example.biljart.network.MatchApiService
import com.example.biljart.network.asDbMatch
import com.example.biljart.network.getMatchesByPlayingDayAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface MatchRepository {
    fun getMatchesByPlayingDay(playingDayId: Int): Flow<List<Match>>
    suspend fun insertMatch(match: Match)
    suspend fun updateMatchScores(matchId: Int, player1FramesWon: Int, player2FramesWon: Int)
    suspend fun refreshMatches(playingdayId: Int)
}

class CashingMatchRepository(
    private val matchDao: MatchDao,
//    private val playerDao: PlayerDao,
    private val playingdayDao: PlayingdayDao,
    private val matchApiService: MatchApiService,
    private val playerRepository: PlayerRepository, // Dependency injection of PlayerRepository
) : MatchRepository {

    override fun getMatchesByPlayingDay(playingDayId: Int): Flow<List<Match>> {
        return matchDao.getMatchesByPlayingDay(playingDayId).map { dbMatches ->
            dbMatches.map { dbMatch ->
                // Fetch players and convert them to domain objects
                val player1 = playerRepository.getById(dbMatch.player1Id).firstOrNull()
                val player2 = playerRepository.getById(dbMatch.player2Id).firstOrNull()
                val playingday = playingdayDao.getById(dbMatch.playingdayId).firstOrNull()?.asDomainObject()

                // Ensure all required data is not null, throw an exception if any are null
                if (player1 == null || player2 == null || playingday == null) {
                    throw IllegalStateException("Data is missing for match id ${dbMatch.matchId}")
                }

                // Convert DbMatch to Match using the converted Player objects
                dbMatch.asDomainObject(player1, player2, playingday)
            }
        }
    }

    override suspend fun insertMatch(match: Match) {
        try {
            // Ensure both players exist before inserting the match
            ensurePlayersExist(match.player1, match.player2)

            // Insert the match
            matchDao.insertMatch(match.asDbMatch())
        } catch (e: Exception) {
            Log.e("MatchRepository", "Error inserting match: ${e.message}", e)
            throw e
        }
    }

    override suspend fun updateMatchScores(matchId: Int, player1FramesWon: Int, player2FramesWon: Int) {
        try {
            matchDao.updateMatchScores(matchId, player1FramesWon, player2FramesWon)
        } catch (e: Exception) {
            Log.e("MatchRepository", "Error updating match scores: ${e.message}", e)
            throw e
        }
    }

    override suspend fun refreshMatches(playingdayId: Int) {
        try {
            // First refresh players to ensure all are available, avoid foreign key constraint errors
            playerRepository.refreshRanking()

            matchApiService.getMatchesByPlayingDayAsFlow(playingdayId)
                .collect { matches ->
                    matches.forEach { apiMatch ->
                        val dbMatch = apiMatch.asDbMatch()
                        matchDao.insertMatch(dbMatch)
                    }
                }
        } catch (e: Exception) {
            Log.e("MatchRepository", "Error refreshing matches: ${e.message}", e)
            throw e
        }
    }
    private suspend fun ensurePlayersExist(player1: Player, player2: Player) {
        playerRepository.getById(player1.playerId).firstOrNull() ?: playerRepository.insert(player1)
        playerRepository.getById(player2.playerId).firstOrNull() ?: playerRepository.insert(player2)
    }
}

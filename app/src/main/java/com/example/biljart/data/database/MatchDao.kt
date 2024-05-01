package com.example.biljart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    // Query to get all matches that belong to a specific playing day
    @Query("SELECT * FROM `match` WHERE playingDayId = :playingDayId")
    fun getMatchesByPlayingDay(playingDayId: Int): Flow<List<DbMatch>>

    // Insert matches into the database
    // ! The validation if the players exist is done in the repository
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: DbMatch)

    // Update frames won for both players in a match by the match ID
    @Query("UPDATE `match` SET player1FramesWon = :player1FramesWon, player2FramesWon = :player2FramesWon WHERE matchId = :matchId")
    suspend fun updateMatchScores(matchId: Int, player1FramesWon: Int, player2FramesWon: Int)
}

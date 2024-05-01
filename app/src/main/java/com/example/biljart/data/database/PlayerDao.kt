package com.example.biljart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player ORDER BY rank ASC")
    fun getAllRanks(): Flow<List<DbPlayer>>

    @Query("SELECT * FROM player WHERE playerId = :playerId") // Not needed for the app, but useful for other entities
    fun getById(playerId: Int): Flow<DbPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // if there is a conflict, replace the old data with the new data
    suspend fun insert(rank: DbPlayer) // Needed in RankingRepository to insert data in the database
}

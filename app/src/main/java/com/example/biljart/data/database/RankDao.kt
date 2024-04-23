package com.example.biljart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RankDao {
    @Query("SELECT * FROM player ORDER BY rank ASC")
    fun getAllRanks(): Flow<List<DbRank>>

    @Query("SELECT * FROM player WHERE player_id = :playerId") // Not needed for the app, but useful for other entities
    fun getById(playerId: Int): Flow<DbRank>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // if there is a conflict, replace the old data with the new data
    suspend fun insert(rank: DbRank) // Not needed for the app, but useful for other entities
}

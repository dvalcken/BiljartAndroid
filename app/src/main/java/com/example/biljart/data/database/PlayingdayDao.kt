package com.example.biljart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayingdayDao {
    @Query("SELECT * FROM playingday")
    fun getAllPlayingdays(): Flow<List<DbPlayingday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playingday: DbPlayingday)
}

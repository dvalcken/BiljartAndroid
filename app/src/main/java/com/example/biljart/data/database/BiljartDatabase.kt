package com.example.biljart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbRank::class, DbPlayingday::class], version = 1) // lesson 9 1u 7'
abstract class BiljartDatabase : RoomDatabase() {
    abstract fun rankDao(): RankDao
    abstract fun playingdayDao(): PlayingdayDao
}

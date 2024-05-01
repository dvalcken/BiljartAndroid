package com.example.biljart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbPlayer::class, DbPlayingday::class, DbMatch::class], version = 1) // lesson 9 1u 7'
abstract class BiljartDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun playingdayDao(): PlayingdayDao
    abstract fun matchDao(): MatchDao
}

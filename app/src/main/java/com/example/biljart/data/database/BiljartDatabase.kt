package com.example.biljart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbRank::class], version = 1) // lesson 9 1u 7'
abstract class BiljartDatabase : RoomDatabase() {
    abstract fun rankDao(): RankDao
}

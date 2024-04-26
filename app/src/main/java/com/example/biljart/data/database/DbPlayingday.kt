package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playingday")
data class DbPlayingday(
    @PrimaryKey
    val playingdayId: Int = 0,
    val date: String = "",
    val isFinished: Boolean = false,
)

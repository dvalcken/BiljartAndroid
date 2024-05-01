package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biljart.model.Playingday

@Entity(tableName = "playingday")
data class DbPlayingday(
    @PrimaryKey
    val playingdayId: Int,
    val date: String,
    val isFinished: Boolean,
)

fun Playingday.asDbPlayingday(): DbPlayingday {
    return DbPlayingday(
        playingdayId = playingdayId,
        date = date,
        isFinished = isFinished,
    )
}

fun DbPlayingday.asDomainObject(): Playingday {
    return Playingday(
        playingdayId = playingdayId,
        date = date,
        isFinished = isFinished,
    )
}

fun List<DbPlayingday>.asDomainObjects(): List<Playingday> {
    return map { it.asDomainObject() }
}

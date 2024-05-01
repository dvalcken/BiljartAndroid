package com.example.biljart.network

import com.example.biljart.model.Playingday
import kotlinx.serialization.Serializable

@Serializable
data class ApiPlayingday(
    val playingday_id: Int,
    val date: String,
    val is_finished: Boolean,
)

fun List<ApiPlayingday>.asDomainObjects(): List<Playingday> {
    return this.map {
        Playingday(
            playingdayId = it.playingday_id,
            date = it.date,
            isFinished = it.is_finished,
        )
    }
}

package com.example.biljart.fake

import com.example.biljart.model.Playingday
import com.example.biljart.network.ApiPlayingday

object FakePlayingdayDataSource {
    val playingday1 = ApiPlayingday(1, "2024-05-01", true)
    val playingday2 = ApiPlayingday(2, "2024-06-01", false)
    val playingday3 = ApiPlayingday(3, "2024-07-01", false)
    val playingday4 = ApiPlayingday(4, "2024-08-01", false)
    val playingdays = listOf(playingday1, playingday2, playingday3, playingday4)
}

fun ApiPlayingday.asDomainObject(): Playingday = Playingday(
    playingdayId = playingday_id,
    date = date,
    isFinished = is_finished,
)

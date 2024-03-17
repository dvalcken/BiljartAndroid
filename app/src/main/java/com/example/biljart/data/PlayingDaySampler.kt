package com.example.biljart.data

import com.example.biljart.model.PlayingDay

object PlayingDaySampler {
    val samplePlayingDays = mutableListOf(
        PlayingDay(1, "2022-01-01", true),
        PlayingDay(2, "2022-01-02", true),
        PlayingDay(3, "2022-01-03", false),
        PlayingDay(4, "2022-01-04", false),
        PlayingDay(5, "2022-01-05", false),
        PlayingDay(6, "2022-01-06", false),
        PlayingDay(7, "2022-01-07", false),
        PlayingDay(8, "2022-01-08", false),
//            PlayingDay(9, "2022-01-09", false),
//            PlayingDay(10, "2022-01-10", false),
//            PlayingDay(11, "2022-01-11", false),
//            PlayingDay(12, "2022-01-12", false),
//            PlayingDay(13, "2022-01-13", false),
//            PlayingDay(14, "2022-01-14", false),
//            PlayingDay(15, "2022-01-15", false),
//            PlayingDay(16, "2022-01-16", false),
//            PlayingDay(17, "2022-01-17", false),
    )

    val getAll: () -> List<PlayingDay> = {
        val list = mutableListOf<PlayingDay>()
        for (item in samplePlayingDays) {
            list.add(
                PlayingDay(
                    item.playingday_id,
                    item.date,
                    item.is_finished,
                ),
            )
        }
        list
    }
}

package com.example.biljart.data

import com.example.biljart.model.Playingday

object PlayingdaySampler {
    val samplePlayingdays = mutableListOf(
        Playingday(1, "2022-01-01", true),
        Playingday(2, "2022-01-02", true),
        Playingday(3, "2022-01-03", false),
        Playingday(4, "2022-01-04", false),
        Playingday(5, "2022-01-05", false),
        Playingday(6, "2022-01-06", false),
        Playingday(7, "2022-01-07", false),
        Playingday(8, "2022-01-08", false),
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

    val getAll: () -> List<Playingday> = {
        val list = mutableListOf<Playingday>()
        for (item in samplePlayingdays) {
            list.add(
                Playingday(
                    item.playingdayId,
                    item.date,
                    item.isFinished,
                ),
            )
        }
        list
    }
}

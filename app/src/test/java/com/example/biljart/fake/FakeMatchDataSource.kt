package com.example.biljart.fake

import com.example.biljart.model.Match
import com.example.biljart.network.ApiMatch

object FakeMatchDataSource {
    val match1 = ApiMatch(
        1,
        2,
        3,
        FakePlayingdayDataSource.playingday1,
        FakePlayerDataSource.player1,
        FakePlayerDataSource.player2,
    )
    val match2 = ApiMatch(
        2,
        3,
        4,
        FakePlayingdayDataSource.playingday1,
        FakePlayerDataSource.player3,
        FakePlayerDataSource.player4,
    )
    val match3 = ApiMatch(
        3,
        4,
        5,
        FakePlayingdayDataSource.playingday2,
        FakePlayerDataSource.player1,
        FakePlayerDataSource.player3,
    )
    val match4 = ApiMatch(
        4,
        1,
        2,
        FakePlayingdayDataSource.playingday2,
        FakePlayerDataSource.player2,
        FakePlayerDataSource.player4,
    )
    val matches = listOf(match1, match2, match3, match4)
}

fun ApiMatch.asDomainObject(): Match {
    return Match(
        matchId = this.matchId,
        player1FramesWon = this.player1FramesWon,
        player2FramesWon = this.player2FramesWon,
        playingday = FakePlayingdayDataSource.playingdays.first { it.playingday_id == this.playingday.playingday_id }.asDomainObject(),
        player1 = FakePlayerDataSource.players.first { it.player_id == this.player1.player_id }.asDomainObject(),
        player2 = FakePlayerDataSource.players.first { it.player_id == this.player2.player_id }.asDomainObject(),
    )
}

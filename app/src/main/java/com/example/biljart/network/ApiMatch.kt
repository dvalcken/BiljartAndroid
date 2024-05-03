@file:OptIn(ExperimentalSerializationApi::class)

package com.example.biljart.network

import com.example.biljart.data.database.DbMatch
import com.example.biljart.model.Match
import com.example.biljart.model.Player
import com.example.biljart.model.Playingday
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ApiMatch(
    @JsonNames("match_id") val matchId: Int,
    @JsonNames("player1FramesWon") val player1FramesWon: Int?, // Nullable because the match might not have been played yet
    @JsonNames("player2FramesWon") val player2FramesWon: Int?, // Nullable because the match might not have been played yet
    @JsonNames("playingDay") val playingday: ApiPlayingday,
    @JsonNames("player1") val player1: ApiPlayer,
    @JsonNames("player2") val player2: ApiPlayer,
)

fun List<ApiMatch>.asDomainObjects(players: Map<Int, Player>, playingDays: Map<Int, Playingday>): List<Match> {
    return this.map { apiMatch ->
        Match(
            matchId = apiMatch.matchId,
            player1FramesWon = apiMatch.player1FramesWon,
            player2FramesWon = apiMatch.player2FramesWon,
            playingday = playingDays[apiMatch.playingday.playingday_id] ?: throw IllegalStateException("Playingday not found for matchId ${apiMatch.matchId}"),
            player1 = players[apiMatch.player1.player_id] ?: throw IllegalStateException("Player1 not found for matchId ${apiMatch.matchId}"),
            player2 = players[apiMatch.player2.player_id] ?: throw IllegalStateException("Player2 not found for matchId ${apiMatch.matchId}"),
        )
    }
}

fun ApiMatch.asDbMatch(): DbMatch {
    return DbMatch(
        matchId = this.matchId,
        player1FramesWon = this.player1FramesWon,
        player2FramesWon = this.player2FramesWon,
        playingdayId = this.playingday.playingday_id,
        player1Id = this.player1.player_id,
        player2Id = this.player2.player_id,
    )
}

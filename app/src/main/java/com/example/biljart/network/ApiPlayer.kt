package com.example.biljart.network

import com.example.biljart.model.Player
import kotlinx.serialization.Serializable

@Serializable
data class ApiPlayer(
    // TODO: fix naming convention errors with @JsonNames like in ApiMatch
    val player_id: Int,
    val name: String,
    val rank: Int,
    val total_frames_won: Int,
    val total_frames_lost: Int,
    val total_matches_won: Int,
    val total_matches_played: Int,
)

fun List<ApiPlayer>.asDomainObjects(): List<Player> { // extension function to convert ApiRank to Rank Les 7 1u22"
    return this.map {
        Player(
            playerId = it.player_id,
            name = it.name,
            rank = it.rank,
            totalFramesWon = it.total_frames_won,
            totalFramesLost = it.total_frames_lost,
            totalMatchesWon = it.total_matches_won,
            totalMatchesPlayed = it.total_matches_played,
        )
    }
}

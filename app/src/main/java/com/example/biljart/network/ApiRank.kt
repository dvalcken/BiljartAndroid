package com.example.biljart.network

import com.example.biljart.model.Rank
import kotlinx.serialization.Serializable

@Serializable
data class ApiRank(
    val player_id: Int,
    val name: String,
    val rank: Int,
    val total_frames_won: Int,
    val total_frames_lost: Int,
    val total_matches_won: Int,
    val total_matches_played: Int,
)

fun List<ApiRank>.asDomainObjects(): List<Rank> { // extension function to convert ApiRank to Rank Les 7 1u22"
    return this.map {
        Rank(
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

/*     {
    "player_id": 37,
    "name": "Piet",
    "rank": 1,
    "total_frames_won": 12,
    "total_frames_lost": 4,
    "total_matches_won": 2,
    "total_matches_played": 2,
    "season": {  // DIT IS ER NIET MEER
        "season_id": 5,
        "season_name": "Android",
        "season_start": "2024-03-11T13:06:41.000Z",
        "framesToWinMatch": 6,
        "is_finished": false
    }
} */
// season: not needed in this app

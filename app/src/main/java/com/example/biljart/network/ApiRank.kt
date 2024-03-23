package com.example.biljart.network

data class ApiRank(
    /*     {
        "player_id": 37,
        "name": "Piet",
        "rank": 1,
        "total_frames_won": 12,
        "total_frames_lost": 4,
        "total_matches_won": 2,
        "total_matches_played": 2,
        "season": {
            "season_id": 5,
            "season_name": "Android",
            "season_start": "2024-03-11T13:06:41.000Z",
            "framesToWinMatch": 6,
            "is_finished": false
        }
    } */

    val player_id: Int,
    val name: String,
    val rank: Int,
    val total_frames_won: Int,
    val total_frames_lost: Int,
    val total_matches_won: Int,
    val total_matches_played: Int,
// season: not needed in this app
)

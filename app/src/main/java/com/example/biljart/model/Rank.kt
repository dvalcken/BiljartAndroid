package com.example.biljart.model

data class Rank(
    var player_id: Int,
    var name: String,
    var rank: Int,
    var total_frames_won: Int = 0,
    var total_frames_lost: Int = 0,
    var total_matches_won: Int = 0,
    var total_matches_played: Int = 0,
)

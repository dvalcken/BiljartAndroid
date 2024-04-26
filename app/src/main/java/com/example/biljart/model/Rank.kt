package com.example.biljart.model

data class Rank(
    var playerId: Int,
    var name: String,
    var rank: Int,
    var totalFramesWon: Int = 0,
    var totalFramesLost: Int = 0,
    var totalMatchesWon: Int = 0,
    var totalMatchesPlayed: Int = 0,
)

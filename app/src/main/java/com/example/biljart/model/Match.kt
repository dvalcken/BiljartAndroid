package com.example.biljart.model

data class Match(
    val matchId: Int,
    val player1FramesWon: Int?, // Nullable because the match might not have been played yet
    val player2FramesWon: Int?, // Nullable because the match might not have been played yet
    val playingday: Playingday,
    val player1: Player,
    val player2: Player,
)

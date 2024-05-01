package com.example.biljart.model

data class Match(
    val matchId: Int,
    val player1FramesWon: Int,
    val player2FramesWon: Int,
    val playingday: Playingday,
    val player1: Player,
    val player2: Player,
)

package com.example.biljart.fake

import com.example.biljart.network.ApiRank

object FakeDataSource {
    val player1 = ApiRank(
        player_id = 1,
        name = "Piet",
        rank = 1,
        total_frames_won = 12,
        total_frames_lost = 4,
        total_matches_won = 2,
        total_matches_played = 2,
    )
    val player2 = ApiRank(
        player_id = 2,
        name = "Klaas",
        rank = 2,
        total_frames_won = 10,
        total_frames_lost = 6,
        total_matches_won = 1,
        total_matches_played = 2,
    )
    val player3 = ApiRank(
        player_id = 3,
        name = "Jan",
        rank = 3,
        total_frames_won = 8,
        total_frames_lost = 8,
        total_matches_won = 1,
        total_matches_played = 2,
    )
    val player4 = ApiRank(
        player_id = 4,
        name = "Kees",
        rank = 4,
        total_frames_won = 6,
        total_frames_lost = 10,
        total_matches_won = 0,
        total_matches_played = 2,
    )

    val ranks = listOf(player1, player2, player3, player4)
}

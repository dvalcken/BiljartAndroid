package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbRank(
    @PrimaryKey // not auto-generated, because id is returned from the database
    var player_id: Int = 0,
    var name: String = "",
    var rank: Int = 0,
    var total_frames_won: Int = 0,
    var total_frames_lost: Int = 0,
    var total_matches_won: Int = 0,
    var total_matches_played: Int = 0,
)

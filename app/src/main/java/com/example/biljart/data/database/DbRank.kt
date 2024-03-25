package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biljart.model.Rank

@Entity(tableName = "player")
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

fun Rank.asDbRank(): DbRank {
    return DbRank(
        player_id = player_id,
        name = name,
        rank = rank,
        total_frames_won = total_frames_won,
        total_frames_lost = total_frames_lost,
        total_matches_won = total_matches_won,
        total_matches_played = total_matches_played,
    )
}

fun DbRank.asDomainObject(): Rank {
    return Rank(
        player_id = player_id,
        name = name,
        rank = rank,
        total_frames_won = total_frames_won,
        total_frames_lost = total_frames_lost,
        total_matches_won = total_matches_won,
        total_matches_played = total_matches_played,
    )
}
fun List<DbRank>.asDomainObjects(): List<Rank> {
    return map { it.asDomainObject() }
}

package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biljart.model.Rank

@Entity(tableName = "player")
data class DbRank(
    @PrimaryKey // not auto-generated, because id is returned from the database
    var playerId: Int = 0,
    var name: String = "",
    var rank: Int = 0,
    var totalFramesWon: Int = 0,
    var totalFramesLost: Int = 0,
    var totalMatchesWon: Int = 0,
    var totalMatchesPlayed: Int = 0,
)

fun Rank.asDbRank(): DbRank {
    return DbRank(
        playerId = playerId,
        name = name,
        rank = rank,
        totalFramesWon = totalFramesWon,
        totalFramesLost = totalFramesLost,
        totalMatchesWon = totalMatchesWon,
        totalMatchesPlayed = totalMatchesPlayed,
    )
}

fun DbRank.asDomainObject(): Rank {
    return Rank(
        playerId = playerId,
        name = name,
        rank = rank,
        totalFramesWon = totalFramesWon,
        totalFramesLost = totalFramesLost,
        totalMatchesWon = totalMatchesWon,
        totalMatchesPlayed = totalMatchesPlayed,
    )
}
fun List<DbRank>.asDomainObjects(): List<Rank> {
    return map { it.asDomainObject() }
}

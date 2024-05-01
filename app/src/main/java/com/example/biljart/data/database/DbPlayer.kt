package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biljart.model.Player

@Entity(tableName = "player")
data class DbPlayer(
    @PrimaryKey // not auto-generated, because id is returned from the database
    var playerId: Int = 0,
    var name: String = "",
    var rank: Int = 0,
    var totalFramesWon: Int = 0,
    var totalFramesLost: Int = 0,
    var totalMatchesWon: Int = 0,
    var totalMatchesPlayed: Int = 0,
)

fun Player.asDbPlayer(): DbPlayer {
    return DbPlayer(
        playerId = playerId,
        name = name,
        rank = rank,
        totalFramesWon = totalFramesWon,
        totalFramesLost = totalFramesLost,
        totalMatchesWon = totalMatchesWon,
        totalMatchesPlayed = totalMatchesPlayed,
    )
}

fun DbPlayer.asDomainObject(): Player {
    return Player(
        playerId = playerId,
        name = name,
        rank = rank,
        totalFramesWon = totalFramesWon,
        totalFramesLost = totalFramesLost,
        totalMatchesWon = totalMatchesWon,
        totalMatchesPlayed = totalMatchesPlayed,
    )
}
fun List<DbPlayer>.asDomainObjects(): List<Player> {
    return map { it.asDomainObject() }
}

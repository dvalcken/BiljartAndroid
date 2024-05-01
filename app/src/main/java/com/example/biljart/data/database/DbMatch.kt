package com.example.biljart.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.biljart.model.Match
import com.example.biljart.model.Player
import com.example.biljart.model.Playingday

@Entity(
    tableName = "match",
    foreignKeys = [
        ForeignKey(
            entity = DbPlayer::class,
            parentColumns = ["playerId"], // This is the column in the parent table, DbPlayer
            childColumns = ["player1Id"], // This is the column in the child table, DbMatch
            onDelete = ForeignKey.CASCADE, // If a player is deleted, delete all matches of that player
        ),
        ForeignKey(
            entity = DbPlayer::class,
            parentColumns = ["playerId"],
            childColumns = ["player2Id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = DbPlayingday::class,
            parentColumns = ["playingdayId"],
            childColumns = ["playingdayId"],
            onDelete = ForeignKey.CASCADE, // If a playing day is deleted, delete all matches of that playing day
        ),
    ],
)
data class DbMatch(
    @PrimaryKey
    val matchId: Int,
    val player1FramesWon: Int,
    val player2FramesWon: Int,
    val playingdayId: Int,
    val player1Id: Int,
    val player2Id: Int,
)

fun DbMatch.asDomainObject(player1: Player, player2: Player, playingDay: Playingday): Match {
    return Match(
        matchId = this.matchId,
        player1 = player1,
        player2 = player2,
        player1FramesWon = this.player1FramesWon,
        player2FramesWon = this.player2FramesWon,
        playingday = playingDay,
    )
}

fun Match.asDbMatch(): DbMatch {
    return DbMatch(
        matchId = this.matchId,
        player1FramesWon = this.player1FramesWon,
        player2FramesWon = this.player2FramesWon,
        playingdayId = this.playingday.playingdayId,
        player1Id = this.player1.playerId,
        player2Id = this.player2.playerId,
    )
}

fun List<DbMatch>.asDomainObjects(players: Map<Int, Player>, playingDays: Map<Int, Playingday>): List<Match> {
    return this.map { dbMatch ->
        val player1 = players[dbMatch.player1Id] ?: throw IllegalStateException("Player1 not found")
        val player2 = players[dbMatch.player2Id] ?: throw IllegalStateException("Player2 not found")
        val playingDay = playingDays[dbMatch.playingdayId] ?: throw IllegalStateException("Playingday not found for matchId ${dbMatch.matchId}")

        dbMatch.asDomainObject(player1, player2, playingDay)
    }
}

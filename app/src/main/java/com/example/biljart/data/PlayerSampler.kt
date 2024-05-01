package com.example.biljart.data

import com.example.biljart.model.Player

object PlayerSampler { // Les 9 1u29 removed, because the data is now fetched from the repository
    val samplePlayers = mutableListOf(
        Player(37, "Piet", 1, 12, 4, 2, 2),
        Player(38, "Klaas", 2, 10, 6, 1, 2),
        Player(39, "Jan", 3, 8, 8, 0, 2),
        Player(40, "Henk", 4, 6, 10, 0, 2),
        Player(41, "Kees", 5, 4, 12, 0, 2),
        Player(42, "Piet", 6, 2, 14, 0, 2),
        Player(43, "Klaas", 7, 0, 16, 0, 2),
        Player(44, "Jan", 8, 0, 18, 0, 2),
        Player(45, "Henk", 9, 0, 20, 0, 2),
        Player(46, "Kees", 10, 0, 22, 0, 2),
        Player(47, "Piet", 11, 0, 24, 0, 2),
        Player(48, "Klaas", 12, 0, 26, 0, 2),
        Player(49, "Jan", 13, 0, 28, 0, 2),
        Player(50, "Henk", 14, 0, 30, 0, 2),
        Player(51, "Kees", 15, 0, 32, 0, 2),
        Player(52, "Piet", 16, 0, 34, 0, 2),
        Player(53, "Klaas", 17, 0, 36, 0, 2),
    )

    val getAll: () -> List<Player> = {
        val list = mutableListOf<Player>()
        for (item in samplePlayers) {
            list.add(
                Player(
                    item.playerId,
                    item.name,
                    item.rank,
                    item.totalFramesWon,
                    item.totalFramesLost,
                    item.totalMatchesWon,
                    item.totalMatchesPlayed,
                ),
            )
        }
        list
    }
}

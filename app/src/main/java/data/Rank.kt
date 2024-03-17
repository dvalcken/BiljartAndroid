package data

data class Rank(
    var player_id: Int,
    var name: String,
    var rank: Int,
    var total_frames_won: Int = 0,
    var total_frames_lost: Int = 0,
    var total_matches_won: Int = 0,
    var total_matches_played: Int = 0,
) {
    companion object {
        val sampleRanks = mutableListOf(
            Rank(37, "Piet", 1, 12, 4, 2, 2),
            Rank(38, "Klaas", 2, 10, 6, 1, 2),
            Rank(39, "Jan", 3, 8, 8, 0, 2),
            Rank(40, "Henk", 4, 6, 10, 0, 2),
            Rank(41, "Kees", 5, 4, 12, 0, 2),
            Rank(42, "Piet", 6, 2, 14, 0, 2),
            Rank(43, "Klaas", 7, 0, 16, 0, 2),
            Rank(44, "Jan", 8, 0, 18, 0, 2),
            Rank(45, "Henk", 9, 0, 20, 0, 2),
            Rank(46, "Kees", 10, 0, 22, 0, 2),
            Rank(47, "Piet", 11, 0, 24, 0, 2),
            Rank(48, "Klaas", 12, 0, 26, 0, 2),
            Rank(49, "Jan", 13, 0, 28, 0, 2),
            Rank(50, "Henk", 14, 0, 30, 0, 2),
            Rank(51, "Kees", 15, 0, 32, 0, 2),
            Rank(52, "Piet", 16, 0, 34, 0, 2),
            Rank(53, "Klaas", 17, 0, 36, 0, 2),
        )

        val getAll: () -> List<Rank> = {
            val list = mutableListOf<Rank>()
            for (item in sampleRanks) {
                list.add(
                    Rank(
                        item.player_id,
                        item.name,
                        item.rank,
                        item.total_frames_won,
                        item.total_frames_lost,
                        item.total_matches_won,
                        item.total_matches_played,
                    ),
                )
            }
            list
        }
    }
}

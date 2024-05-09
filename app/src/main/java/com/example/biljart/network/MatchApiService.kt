package com.example.biljart.network

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface MatchApiService {
    // Get all matches for a specific playing day
    @GET("api/match/playingday")
    suspend fun getMatchesByPlayingDay(@Query("playingday_id") playingDayId: Int): List<ApiMatch>

    // Endpoint to update a match, for example:
    // @PUT("http://localhost:9000/api/match/updateandroid?match_id=131&player1FramesWon=6&player2FramesWon=5")
    @PUT("api/match/updateandroid")
    suspend fun updateMatchScores(
        @Query("match_id") matchId: Int,
        @Query("player1FramesWon") player1FramesWon: Int,
        @Query("player2FramesWon") player2FramesWon: Int,
    ): Response<Unit> // Response object is returned to check if the request was successful in the repository
}

// Helper function similar to the PlayerApiService
fun MatchApiService.getMatchesByPlayingDayAsFlow(playingDayId: Int) = flow {
    emit(getMatchesByPlayingDay(playingDayId))
}

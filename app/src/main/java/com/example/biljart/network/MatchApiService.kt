package com.example.biljart.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApiService {
    // Get all matches for a specific playing day
    @GET("api/match/playingday")
    suspend fun getMatchesByPlayingDay(@Query("playingday_id") playingDayId: Int): List<ApiMatch>

    // TODO
    // Other endpoint to update a match, for example:
    // @PUT("http://localhost:9000/api/match/updateandroid?match_id=131&player1FramesWon=6&player2FramesWon=5")
    // suspend fun updateMatchScores(@Body scoreUpdateRequest: ScoreUpdateRequest): Response<ApiResponse>
}

// Helper function similar to the PlayerApiService
fun MatchApiService.getMatchesByPlayingDayAsFlow(playingDayId: Int) = flow {
    emit(getMatchesByPlayingDay(playingDayId))
}

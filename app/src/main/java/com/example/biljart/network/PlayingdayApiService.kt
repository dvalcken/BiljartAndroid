package com.example.biljart.network

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PlayingdayApiService {
    @GET("api/playingday/android")
    suspend fun getAllPlayingdays(): List<ApiPlayingday>

    @PUT("api/playingday/updateandroid")
    suspend fun updatePlayingdayStatus(
        @Query("playingday_id") playingdayId: Int,
        @Query("is_finished") isFinished: Boolean,
    ): Response<Unit>
}

fun PlayingdayApiService.getPlayingdaysAsFlow() = flow { emit(getAllPlayingdays()) }

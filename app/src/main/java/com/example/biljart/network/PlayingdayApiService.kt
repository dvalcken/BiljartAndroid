package com.example.biljart.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

interface PlayingdayApiService {
    @GET("api/playingday/android")
    suspend fun getAllPlayingdays(): List<ApiPlayingday>
}

fun PlayingdayApiService.getPlayingdaysAsFlow() = flow { emit(getAllPlayingdays()) }

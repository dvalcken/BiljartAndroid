package com.example.biljart.network

import retrofit2.http.GET

// define the API service
interface RankingApiService {
    // suspend is added to force the user to call the function from a coroutine
    @GET("api/season/android")
    suspend fun getRank(): List<ApiRank>
}

// base URL and retrofit code: moved to AppContainer.kt (lesson 8 16')

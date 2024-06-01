package com.example.biljart.network

import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

// define the API service
interface PlayerApiService {
    // suspend is added to force the user to call the function from a coroutine
    @GET("api/season/android")
    suspend fun getAllRanks(): List<ApiPlayer>
}

// base URL and retrofit code: moved to AppContainer.kt (lesson 8 16')

// helper function: this is in fact an extension function on PlayerApiService
fun PlayerApiService.getPlayerssAsFlow() = flow { emit(getAllRanks()) } // this function is used in the RankingRepository (lesson 9 58'40") to convert the List<ApiRank> to a Flow<List<ApiRank>>

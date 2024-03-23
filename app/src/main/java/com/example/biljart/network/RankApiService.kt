package com.example.biljart.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface RankApiService {
    suspend fun getRank(): List<ApiRank>
}

private const val BASE_URL = "http://10.0.2.2:3000/"

private var retrofit: Retrofit = Retrofit.Builder() // this creates a new instance of Retrofit
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // if the server returns JSON, this will convert it to a List<ApiRank>
    .baseUrl(BASE_URL)
    .build()

object RankApi { // this object is a singleton
    val rankService: RankApiService by lazy { // this creates a new instance of RankApiService only when it is needed via 'by lazy'
        retrofit.create(RankApiService::class.java) // this creates a new instance of the RankApiService using the Retrofit instance
    }
}

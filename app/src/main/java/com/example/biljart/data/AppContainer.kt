package com.example.biljart.data

import com.example.biljart.network.RankingApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// the container contains all the dependencies of the app  (Les 8 40")
// is responsible for initializing objects (and thus also for initializing the repository)
// the container is initialized in the BiljartApplication.kt class (Les 8 41'30")
interface AppContainer { // Les 8 8'
    val rankingRepository: RankingRepository // val is a property, so has getter and setter
    /* TODO: add other repositories here */
}

class DefaultAppContainer() : AppContainer { // Les 8 8' and later
    // this is now a class, so it should be created, and this will be done in the new BiljartApplication.kt class (Les 8 20'30")

    private val baseUrl = "http://10.0.2.2:9000/"
// private const val BASE_URL = "http://localhost:9000/"

    private var retrofit: Retrofit = Retrofit.Builder() // this creates a new instance of Retrofit
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // if the server returns JSON, this will convert it to a List<ApiRank>
        .baseUrl(baseUrl)
        .build()

    private val rankingService: RankingApiService by lazy { // this creates a new instance of RankApiService only when it is needed via 'by lazy'
        retrofit.create(RankingApiService::class.java) // this creates a new instance of the RankApiService using the Retrofit instance
    }

    override val rankingRepository: RankingRepository by lazy {
        ApiRankingRepository(rankingService) // this is a lambda function that creates a new instance of the ApiRankingRepository AND returns it
    }
}

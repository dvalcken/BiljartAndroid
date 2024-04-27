package com.example.biljart.data

import android.content.Context
import androidx.room.Room
import com.example.biljart.data.database.BiljartDatabase
import com.example.biljart.data.database.PlayingdayDao
import com.example.biljart.data.database.RankDao
import com.example.biljart.network.PlayingdayApiService
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
    val playingdayRepository: PlayingdayRepository
/* TODO: add other repositories here */
}

class DefaultAppContainer(
    private val applicationContext: Context,
) : AppContainer { // Les 8 8' and later. Les 9 1u8' for applicationContext
    // this is now a class, so it should be created, and this will be done in the new BiljartApplication.kt class (Les 8 20'30")

    private val baseUrl = "http://10.0.2.2:9000/"
// private const val BASE_URL = "http://localhost:9000/"

    // this is the JSON parser that will be used by Retrofit to parse the JSON data
    // It is configured to ignore unknown keys, be lenient in JSON format, and to coerce input values
    // ! This is different from the JSON parser that is used in the lesson, and used to avoid errors in the JSON parsing when the server returns extra keys that are not in the data class
    private val json = Json {
        ignoreUnknownKeys = true // Ignore unknown JSON keys (if the server returns extra keys that are not in the data class)
        isLenient = true // Allow some leniency in JSON format, e.g. trailing commas
        coerceInputValues = true // Try to coerce incorrect JSON values into expected types, e.g. coerce a null value to a default value for primitives)
    }

    private var retrofit: Retrofit = Retrofit.Builder() // this creates a new instance of Retrofit
        // in the following line, the default JSON parser (Json) is replaced by the JSON parser that is defined above (json)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // if the server returns JSON, this will convert it to a List<ApiRank>
        .baseUrl(baseUrl)
        .build()

    private val biljartDb: BiljartDatabase by lazy { // Les 9 1u 12' this creates a new instance of the BiljartDatabase
        Room.databaseBuilder(applicationContext, BiljartDatabase::class.java, "biljart-database")
            .build()
    }

    private val rankingService: RankingApiService by lazy { // this creates a new instance of RankApiService only when it is needed via 'by lazy'
        retrofit.create(RankingApiService::class.java) // this creates a new instance of the RankApiService using the Retrofit instance
    }

    private val rankDao: RankDao by lazy { // Les 9 1u 6' this creates a new instance of the RankDao
        biljartDb.rankDao()
    }

    override val rankingRepository: RankingRepository by lazy {
        // ApiRankingRepository(rankingService) // this is a lambda function that creates a new instance of the ApiRankingRepository AND returns it
        CashingRankingRepository(rankDao = rankDao, rankingApiService = rankingService) // This replaces the line above (Les 9 1u 05')
    }

    private val playingdayService: PlayingdayApiService by lazy {
        retrofit.create(PlayingdayApiService::class.java)
    }

    private val playingdayDao: PlayingdayDao by lazy {
        biljartDb.playingdayDao()
    }

    override val playingdayRepository: PlayingdayRepository by lazy {
        CashingPlayingdayRepository(playingdayDao = playingdayDao, playingdayApiService = playingdayService)
    }
}

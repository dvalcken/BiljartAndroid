package com.example.biljart.data

import android.util.Log
import com.example.biljart.data.database.PlayingdayDao
import com.example.biljart.data.database.asDbPlayingday
import com.example.biljart.data.database.asDomainObjects
import com.example.biljart.model.Playingday
import com.example.biljart.network.PlayingdayApiService
import com.example.biljart.network.asDomainObjects
import com.example.biljart.network.getPlayingdaysAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface PlayingdayRepository {
    fun getAllPlayingdays(): Flow<List<Playingday>>

    suspend fun insert(playingday: Playingday)

    suspend fun refreshPlayingdays()
}

class CashingPlayingdayRepository(
    private val playingdayApiService: PlayingdayApiService,
    private val playingdayDao: PlayingdayDao,
) : PlayingdayRepository {
    override fun getAllPlayingdays(): Flow<List<Playingday>> {
        return playingdayDao.getAllPlayingdays().map { it.asDomainObjects() }
            .onEach {
                if (it.isEmpty()) {
                    try {
                        refreshPlayingdays()
                    } catch (e: SocketTimeoutException) {
                        Log.e("CashingPlayingdayRepository getAllPlayingdays on clean start SocketTimeoutException", "Network timeout on clean start when getAllPlayingdays: ${e.message}", e)
                        // throw e  // Error is not rethrown, PlayingdayApiState.Error is set in the ViewModel, and user is informed with a ErrorMessageComponent
                    } catch (e: Exception) {
                        Log.w("CashingPlayingdayRepository getAllPlayingdays-method error", "CashingPlayingdayRepository getAllPlayingdays-method error: ${e.message}", e)
                        throw e
                    }
                }
            }
    }

    override suspend fun insert(playingday: Playingday) {
        try {
            playingdayDao.insert(playingday.asDbPlayingday())
        } catch (e: Exception) {
            Log.e("CashingPlayingdayRepository insert-method error", "CashingPlayingdayRepository insert-method error: ${e.message}", e)
            throw e
        }
    }

    override suspend fun refreshPlayingdays() {
        try {
            playingdayApiService.getPlayingdaysAsFlow().collect {
                for (playingday in it.asDomainObjects()) {
                    Log.i("PlayingdayRepository", "Refreshing playingday: $playingday")
                    playingdayDao.insert(playingday.asDbPlayingday())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("CashingPlayingdayRepository refreshPlayingdays", "Network timeout when refreshing playingdays: ${e.message}", e)
            throw e
        } catch (e: Exception) {
            Log.w("CashingPlayingdayRepository refreshPlayingdays-method error", "CashingPlayingdayRepository refreshPlayingdays-method error: ${e.message}", e)
            throw e
        }
    }
}

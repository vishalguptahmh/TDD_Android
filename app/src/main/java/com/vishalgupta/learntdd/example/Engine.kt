package com.vishalgupta.learntdd.example

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Engine(var isTurnedOn: Boolean = false,var temperature: Int = 15) {

    val TAG = "Engine"
    suspend fun turnOnEngine(): Flow<Int> {
        isTurnedOn = true
        return flow {
            while (temperature <= 75) {
                delay(2000)
                temperature += 25
                emit(temperature)

            }
            Log.d(TAG, "turnOnEngine: Started")
        }



    }
}
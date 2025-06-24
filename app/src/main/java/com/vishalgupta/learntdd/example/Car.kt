package com.vishalgupta.learntdd.example

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  Created by vishal.gupta on 22/06/25
 */
class Car(var fuel: Double,val engine: Engine) {

    fun turnOn() {
        fuel-=0.5
        CoroutineScope(Dispatchers.Main).launch {
            engine.turnOnEngine().collect { temperature ->
                Log.d("Car", "Engine temperature: $temperature")
            }
        }
    }
}
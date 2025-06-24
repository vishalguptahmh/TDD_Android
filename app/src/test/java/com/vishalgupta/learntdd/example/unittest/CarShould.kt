package com.vishalgupta.learntdd.example.unittest

import com.vishalgupta.learntdd.example.Car
import com.vishalgupta.learntdd.example.Engine
import com.vishalgupta.learntdd.utils.MainCoroutineScopeRule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.times
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.kotlin.whenever

/**
 *  Created by vishal.gupta on 22/06/25
 */
class CarShould {
    val engine : Engine = mock()
    lateinit var car : Car


    @get:Rule
    var coroutineRule = MainCoroutineScopeRule()


    init {
        runTest {
            whenever(engine.turnOnEngine()).thenReturn(flow {
                emit(40)
                emit(65)
                emit(90)
            })
           car =  Car(5.0,engine)
        }
    }
    @Test
    fun looseFuelWhenTurnedOn()= runTest {
        car.turnOn()
        assertEquals(4.5,car.fuel)
    }

    @Test
    fun turnOnItsEngine() = runTest {
        car.turnOn()
        coroutineRule.scope.advanceTimeBy(5000)
        verify(engine,times(1)).turnOnEngine()
    }
}
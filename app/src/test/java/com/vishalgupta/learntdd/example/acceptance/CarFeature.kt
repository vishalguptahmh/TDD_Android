package com.vishalgupta.learntdd.example.acceptance

import com.vishalgupta.learntdd.example.Car
import com.vishalgupta.learntdd.example.Engine
import com.vishalgupta.learntdd.utils.MainCoroutineScopeRule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

/**
 *here we don't mock the car or Engine we are validating real objects with whole integration test
 * integration test can be with 2 more classes
 * In Unit test we mock the classes and test the functionality of a single class
 *
 */
class CarFeature {

   private val engine = Engine()
    private val car = Car(6.0,engine)

    @get:Rule
    var coroutineRule = MainCoroutineScopeRule()

    @Test
    fun carIsLoosingFuelWhenTurningOn(): Unit = runTest {
        car.turnOn()
        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnitsEngineAndIncreasesTemperatureGradually(): Unit = coroutineRule.runTest {
        car.turnOn()
        advanceTimeBy(6001) // Advance time to allow the coroutine to complete
        assertEquals(90, car.engine.temperature)
        assertEquals(car.engine.isTurnedOn, true)
    }


}
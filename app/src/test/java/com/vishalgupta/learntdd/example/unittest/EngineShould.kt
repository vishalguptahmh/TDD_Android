package com.vishalgupta.learntdd.example.unittest

import com.vishalgupta.learntdd.example.Engine
import com.vishalgupta.learntdd.utils.MainCoroutineScopeRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


/**
 *  Created by vishal.gupta on 22/06/25
 */
class EngineShould {

    private val engine= Engine()

    @get:Rule
    var coroutineRule = MainCoroutineScopeRule()

    @Test
    fun turnOn() = runTest {
        engine.turnOnEngine()
        Assert.assertEquals(true, engine.isTurnedOn)
    }

    @Test
    fun turnOnIncreaseTemperatureGradually(): Unit  = runTest{
       val flow  =  engine.turnOnEngine()
        val actual = flow.toList()

        Assert.assertEquals(listOf<Int>(40,65,90),actual)
    }
}
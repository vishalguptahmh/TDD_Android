package com.vishalgupta.learntdd.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.mockito.Mockito.mock
/**
 *  Created by vishal.gupta on 24/06/25
 */
open class BaseUnitTest {
    internal val exception = RuntimeException("Network Error")

    @get:Rule
    val testCoroutineRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}
package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.MainActivityViewModel
import com.vishalgupta.learntdd.PlayList
import com.vishalgupta.learntdd.PlayListRepository
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *  Created by vishal.gupta on 23/06/25
 */
class MainActivityViewModelTestShould : BaseUnitTest(){

    val repository: PlayListRepository = mock()
    val playlist = mock<List<PlayList>>()
    val expectedResult = Result.success(playlist)
    val exception = RuntimeException("Network Error")
    @Test
    fun getPlayListFromRepository(): Unit = runTest {
        initViewModel()
        verify(repository, times(1)).getPlayList()
    }

    @Test
    fun emitPlayListFromRepostiry() = runTest {
        val result = initViewModel()
        assertEquals(expectedResult, result)
    }

    @Test
    fun emitErrorMessage() = runTest {
        whenever(repository.getPlayList()).thenReturn(flow {
            emit(Result.failure(exception))
        })
        val viewModel = MainActivityViewModel(repository)
        val result = viewModel.playList.first { it != null }
        assertEquals(exception, result?.exceptionOrNull())
        assertEquals("Network Error", result?.exceptionOrNull()?.message)
    }
    private suspend fun initViewModel(): Result<List<PlayList>>? {
        whenever(repository.getPlayList()).thenReturn(flow {
            emit(expectedResult)
        })
        val viewModel = MainActivityViewModel(repository)
        val result = viewModel.playList.first { it != null }
        return result
    }
}
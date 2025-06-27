package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.MainActivityViewModel
import com.vishalgupta.learntdd.playList.PlayList
import com.vishalgupta.learntdd.playList.PlayListRepository
import com.vishalgupta.learntdd.playListDetail.PlayListDetailUsecase
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
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
class MainActivityViewModelTestShould : BaseUnitTest() {
    val repository: PlayListRepository = mock()
    val playListDetailUsecase: PlayListDetailUsecase = mock()
    val playlist = mock<List<PlayList>>()
    val expectedResult = Result.success(playlist)

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
        val viewModel = viewModelFailure()
        val result = viewModel.playList.first { it != null }
        assertEquals(exception, result?.exceptionOrNull())
        assertEquals("Network Error", result?.exceptionOrNull()?.message)
    }

    @Test
    fun ShowSpinnerWhileLoading(): Unit = runTest {
        val viewModel = viewModelSuccess(true)
        val loadingStates = mutableListOf<Boolean>()
        val job = launch { viewModel.isLoading.toList(loadingStates) }
        // After the data is loaded, the loading state should be false
        viewModel.playList.first { it != null }
        delay(500) // job was closing immediately, so we need to wait for the flow to emit for loader and collect it
        job.cancel()

        println("Loading States: $loadingStates")
        assertEquals(2, loadingStates.size)
        assertEquals(true, loadingStates.contains(true))
        assertEquals(false, loadingStates.last())
    }

    @Test
    fun CloseSpinnerOnError(): Unit = runTest {
        val viewModel = viewModelFailure(true)
        val loadingStates = mutableListOf<Boolean>()
        val job = launch {
            viewModel.isLoading.toList(loadingStates)
        }
        // After the data is loaded, the loading state should be false
        viewModel.playList.first { it != null }
        delay(500) // job was closing immediately, so we need to wait for the flow to emit for loader and collect it
        job.cancel()

        assertEquals(2, loadingStates.size)
        assertEquals(true, loadingStates.contains(true))
        assertEquals(false, loadingStates.last())
    }

    private suspend fun initViewModel(): Result<List<PlayList>>? {
        val viewModel = viewModelSuccess()
        val result = viewModel.playList.first { it != null }
        return result
    }

    private suspend fun viewModelSuccess(delayNeeded: Boolean = false): MainActivityViewModel {
        whenever(repository.getPlayList()).thenReturn(flow {
            if (delayNeeded) {
                delay(500) // Simulate network delay
            }
            emit(expectedResult)
        })
        return MainActivityViewModel(repository,playListDetailUsecase)
    }

    private suspend fun viewModelFailure(delayNeeded: Boolean = false): MainActivityViewModel {
        whenever(repository.getPlayList()).thenReturn(flow {
            if (delayNeeded) {
                delay(500) // Simulate network delay
            }
            emit(Result.failure(exception))
        })
        return MainActivityViewModel(repository,playListDetailUsecase)
    }
}
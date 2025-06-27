package com.vishalgupta.learntdd.playlistDetail

import com.vishalgupta.learntdd.MainActivityViewModel
import com.vishalgupta.learntdd.playList.PlayListRepository
import com.vishalgupta.learntdd.playListDetail.PlayListDetail
import com.vishalgupta.learntdd.playListDetail.PlayListDetailUsecase
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

/**
 *  Created by vishal.gupta on 26/06/25
 */
class PlayListDetailUnitTestShould : BaseUnitTest() {
    val repo: PlayListRepository = Mockito.mock()
    val usecase: PlayListDetailUsecase = mock()
    val playListDetail = Mockito.mock<PlayListDetail>()

    @Test
    fun showHideLoader() = runTest {
        val viewModel = successResultFromRespository()
        val loadingStates = mutableListOf<Boolean>()
        viewModel.getPlayListDetails(1)
        val job = launch { viewModel.isLoading.toList(loadingStates) }
        advanceUntilIdle()
        job.cancel()
        assertEquals(2, loadingStates.size)
        assertEquals(true, loadingStates.contains(true))
        assertEquals(false, loadingStates.last())
    }

    @Test
    fun repostioryIsCalled() = runTest {
        val viewModel = successResultFromRespository()
        viewModel.getPlayListDetails(1)
        viewModel.playListDetail.first { it != null }
        verify(usecase, times(1)).getPlayListDetail(1)
    }

    @Test
    fun getExceptionFromNetwork()= runTest {
        val viewModel = failureRepo()
        viewModel.getPlayListDetails(1)
        val result = viewModel.playListDetail.first { it != null }
        assertEquals(exception, result?.exceptionOrNull())
        assertEquals("Network Error", result?.exceptionOrNull()?.message)
    }

    @Test
    fun getDetailsFromRepository()= runTest {
        val viewModel = successResultFromRespository()
        viewModel.getPlayListDetails(1)
        val result = viewModel.playListDetail.first { it != null }
        assertEquals(playListDetail, result?.getOrNull())
    }


    fun successResultFromRespository(): MainActivityViewModel {
        whenever(usecase.getPlayListDetail(1)).thenReturn(flow {
            delay(900)
            emit(Result.success(playListDetail))
        })
        return MainActivityViewModel(repository = repo,usecase)
    }

    fun failureRepo(): MainActivityViewModel {
        whenever(usecase.getPlayListDetail(1)).thenReturn(flow {
            emit(Result.failure(exception))
        })
        return MainActivityViewModel(repo,usecase)
    }
}
package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.PlayListRepository
import com.vishalgupta.learntdd.PlayListService
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

/**
 *  Created by vishal.gupta on 23/06/25
 */
class PlayListRepostoryTestShould : BaseUnitTest() {
    val service: PlayListService = mock()
    val playlist = mock<List<com.vishalgupta.learntdd.PlayList>>()
    val exception = RuntimeException("Network Error")

    @Test
    fun getPlayListFromService() = runTest {
        val repository = PlayListRepository(service)
        repository.getPlayList()
        verify(service, times(1)).fetchPlayList()
    }

    @Test
    fun emitPlayListFromService(): Unit = runTest {
        val repository = playListRepository()
        assertEquals(playlist, repository.getPlayList().first().getOrNull())
    }

    @Test
    fun emitErrorFromService() = runTest {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        val repository = PlayListRepository(service)
        val result = repository.getPlayList().first { it.isFailure }

        assertEquals(result.exceptionOrNull(), exception)
        assertEquals(result.exceptionOrNull()?.message, "Network Error")
    }
}

private fun PlayListRepostoryTestShould.playListRepository(): PlayListRepository {
    whenever(service.fetchPlayList()).thenReturn(
        flow {
            emit(Result.success(playlist))
        }
    )
    val repository = PlayListRepository(service)
    return repository
}
package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.PlayListMapper
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
    val playListRaw = mock<List<com.vishalgupta.learntdd.PlayListRaw>>()
    val exception = RuntimeException("Network Error")
    private val mapper: PlayListMapper = mock()

    @Test
    fun getPlayListFromService() = runTest {
        val repository = PlayListRepository(service, mapper)
        repository.getPlayList()
        verify(service, times(1)).fetchPlayList()
    }

    @Test
    fun emitPlayListFromService(): Unit = runTest {
        val repository = playListRepositorySuccess()
        assertEquals(playlist, repository.getPlayList().first().getOrNull())
    }

    @Test
    fun emitErrorFromService() = runTest {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        val repository = PlayListRepository(service, mapper)
        val result = repository.getPlayList().first { it.isFailure }

        assertEquals(result.exceptionOrNull(), exception)
        assertEquals(result.exceptionOrNull()?.message, "Network Error")
    }

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = playListRepositorySuccess()
        repository.getPlayList().first()
        verify(mapper, times(1)).invoke(playListRaw)
    }

    fun playListRepositorySuccess(): PlayListRepository {
        whenever(service.fetchPlayList()).thenReturn(
            flow { emit(Result.success(playListRaw)) }
        )
        whenever(mapper.invoke(playListRaw)).thenReturn(playlist)
        val repository = PlayListRepository(service, mapper)
        return repository
    }
}
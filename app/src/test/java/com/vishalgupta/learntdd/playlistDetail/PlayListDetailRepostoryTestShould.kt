package com.vishalgupta.learntdd.playlistDetail

import com.vishalgupta.learntdd.playList.PlayListMapper
import com.vishalgupta.learntdd.playList.PlayListRepository
import com.vishalgupta.learntdd.playList.PlayListService
import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw
import com.vishalgupta.learntdd.playListDetail.PlayListDetail
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
class PlayListDetailRepostoryTestShould : BaseUnitTest() {
    val service: PlayListService = mock()
    val playListRaw = mock<PlayListDetailRaw>()
    private val mapper: PlayListMapper = mock()

    @Test
    fun getPlayListDetailFromService() = runTest {
        val repository = PlayListRepository(service, mapper)
        repository.getPlayListDetail(1)
        verify(service, times(1)).fetchPlayListDetail(1)
    }

    @Test
    fun emitPlayListDetailFromService(): Unit = runTest {
        val repository = playListRepositorySuccess()
        val result = repository.getPlayListDetail(1).first().getOrNull()
        assertEquals(playListRaw, result)
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

    fun playListRepositorySuccess(): PlayListRepository {
        whenever(service.fetchPlayListDetail(1)).thenReturn(
            flow { emit(Result.success(playListRaw)) }
        )
        val repository = PlayListRepository(service, mapper)
        return repository
    }
}
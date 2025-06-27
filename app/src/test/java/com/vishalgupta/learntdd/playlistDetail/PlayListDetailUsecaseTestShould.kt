package com.vishalgupta.learntdd.playlistDetail

import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw
import com.vishalgupta.learntdd.playList.PlayListRepository
import com.vishalgupta.learntdd.playListDetail.PlayListDetail
import com.vishalgupta.learntdd.playListDetail.PlayListDetailMapper
import com.vishalgupta.learntdd.playListDetail.PlayListDetailUsecase
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.delay
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
 *  Created by vishal.gupta on 27/06/25
 */
class PlayListDetailUsecaseTestShould : BaseUnitTest() {
    val repo: PlayListRepository = mock()
    val rawDetails: PlayListDetailRaw = mock()
    val details: PlayListDetail = mock()
    val mapper: PlayListDetailMapper = mock()

    @Test
    fun verifyRepoIsCalled() = runTest {
        val usecase = successResultFromRepository()
        usecase.getPlayListDetail(1).first { it != null }
        verify(repo, times(1)).getPlayListDetail(1)
    }

    @Test
    fun convertRawDetailToDetails() = runTest {
        val usecase = successResultFromRepository()
        val result = usecase.getPlayListDetail(1).first { it != null }
        assertEquals(details, result.getOrNull())
    }

    @Test
    fun getPlayListDetailFromRepostory() = runTest {
        val usecase = successResultFromRepository()
        val result = usecase.getPlayListDetail(1).first()
        assertEquals(Result.success(details), result)
    }

    @Test
    fun emitException() = runTest {
        val usecase = failureResultFromRepository()
        val result = usecase.getPlayListDetail(1).first { it != null }
        assertEquals(exception, result.exceptionOrNull())
    }

    fun successResultFromRepository(): PlayListDetailUsecase {
        whenever(mapper.invoke(rawDetails)).thenReturn(details)
        whenever(repo.getPlayListDetail(1)).thenReturn(
            flow {
                delay(500)
                emit(Result.success(rawDetails))
            }
        )
        return PlayListDetailUsecase(repo, mapper)
    }

    fun failureResultFromRepository(): PlayListDetailUsecase {
        whenever(repo.getPlayListDetail(1)).thenReturn(
            flow {
                delay(500)
                emit(Result.failure(exception))
            }
        )
        return PlayListDetailUsecase(repo, mapper)
    }
}
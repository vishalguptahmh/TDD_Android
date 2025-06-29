package com.vishalgupta.learntdd.playlist

import com.vishalgupta.learntdd.core.AppApi
import com.vishalgupta.learntdd.core.PlayListRaw
import com.vishalgupta.learntdd.playList.PlayListService
import com.vishalgupta.learntdd.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

/**
 *  Created by vishal.gupta on 24/06/25
 */
class PlayListServiceTestShould : BaseUnitTest() {
    val playList = mock<List<PlayListRaw>>()
    val api: AppApi = mock()

    @Test
    fun getListFromApi() = runTest {
        val service = SuccessMockservice()
        service.fetchPlayList().first()
        verify(api, times(1)).getPlayList()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runTest {
        val service = SuccessMockservice()
        assertEquals(Result.success(playList), service.fetchPlayList().first())
    }

    @Test
    fun emitErrorTestWhenNetworkFails() = runTest {
        val service = failureMockService()
        val result = service.fetchPlayList().first().exceptionOrNull()

        assert(result == exception)
        assert(result?.message == "Network Error")
    }

    private suspend fun failureMockService(): PlayListService {
        whenever(api.getPlayList()).thenThrow(exception)
        val service = PlayListService(api)
        return service
    }

    private suspend fun SuccessMockservice(): PlayListService {
        whenever(api.getPlayList()).thenReturn(playList)
        val service = PlayListService(api)
        return service
    }

}


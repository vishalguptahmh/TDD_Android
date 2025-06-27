package com.vishalgupta.learntdd.playList

import android.util.Log
import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw
import com.vishalgupta.learntdd.playList.PlayListService
import com.vishalgupta.learntdd.playListDetail.PlayListDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *  Created by vishal.gupta on 23/06/25
 */
class PlayListRepository @Inject constructor(private val service: PlayListService, private val mapper: PlayListMapper) {
    val TAG = "PlayListRepository"
    fun getPlayList(): Flow<Result<List<PlayList>>> {
        Log.d("PlayListRepository", "getPlayList: ")
        return service.fetchPlayList()
            .map {
                if (it.isSuccess) {
                    Result.success(mapper(it.getOrNull()!!))
                } else {
                    Log.e(TAG, "getPlayList: Error fetching playlist", it.exceptionOrNull())
                    Result.failure(it.exceptionOrNull() ?: Exception("Unknown error"))
                }
            }
    }
    fun getPlayListDetail(id: String): Flow<Result<PlayListDetailRaw>> {
        Log.d("PlayListRepository", "getPlayList: ")
        return service.fetchPlayListDetail(id)
    }
}
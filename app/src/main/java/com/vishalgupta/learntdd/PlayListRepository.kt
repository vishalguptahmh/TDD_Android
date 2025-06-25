package com.vishalgupta.learntdd

import android.util.Log
import kotlinx.coroutines.flow.Flow
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
}
package com.vishalgupta.learntdd

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *  Created by vishal.gupta on 23/06/25
 */
class PlayListRepository @Inject constructor(private val service: PlayListService) {
    val TAG = "PlayListRepository"
     suspend fun getPlayList(): Flow<Result<List<PlayList>>> {
         Log.d("PlayListRepository", "getPlayList: ")
         val list=  service.fetchPlayList()

         Log.d(TAG, "getPlayList: "+list.first().getOrNull())
         return list
    }
}
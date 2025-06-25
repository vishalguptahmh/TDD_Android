package com.vishalgupta.learntdd

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayListService @Inject constructor(val api: PlayListApi) {
    fun fetchPlayList(): Flow<Result<List<PlayListRaw>>> {
        Log.d("PlayListService", "fetchPlayList: ")
        return flow {
            emit(Result.success(api.getPlayList()))
        }.catch {
            emit(Result.failure(it))
        }
    }
}
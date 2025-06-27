package com.vishalgupta.learntdd.playList

import android.util.Log
import com.vishalgupta.learntdd.core.AppApi
import com.vishalgupta.learntdd.core.PlayListRaw
import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayListService @Inject constructor(val api: AppApi) {
    fun fetchPlayList(): Flow<Result<List<PlayListRaw>>> {
        Log.d("PlayListService", "fetchPlayList: ")
        return flow {
            emit(Result.success(api.getPlayList()))
        }.catch {
            emit(Result.failure(it))
        }
    }

    fun fetchPlayListDetail(i: String): Flow<Result<PlayListDetailRaw>> {
        Log.d("PlayListService", "fetchPlayListDetail: ")
        return flow {
            emit(Result.success(api.getPlayDetails(i)))
        }.catch { emit(Result.failure(it)) }
    }
}
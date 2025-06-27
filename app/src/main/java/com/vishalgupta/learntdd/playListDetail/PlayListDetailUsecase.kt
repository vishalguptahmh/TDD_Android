package com.vishalgupta.learntdd.playListDetail

import android.util.Log
import com.vishalgupta.learntdd.playList.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayListDetailUsecase @Inject constructor(val repo: PlayListRepository,val mapper: PlayListDetailMapper) {
    fun getPlayListDetail(id: String) :Flow<Result<PlayListDetail>>{
        Log.d("PlayListDetailUsecase", "getPlayListDetail: $id")
        return repo.getPlayListDetail(id).map {
            if(it.isSuccess){
              Result.success(mapper(it.getOrNull()!!))
            } else {
                Result.failure(it.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }
}
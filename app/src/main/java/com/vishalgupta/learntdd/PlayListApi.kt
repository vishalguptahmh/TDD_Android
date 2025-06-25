package com.vishalgupta.learntdd

import retrofit2.http.GET

interface PlayListApi {

    @GET("playlist")
    suspend fun getPlayList():List<PlayListRaw>

}
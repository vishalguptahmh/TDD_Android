package com.vishalgupta.learntdd.core

import com.vishalgupta.learntdd.core.playlistDetail.PlayListDetailRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("playlist")
    suspend fun getPlayList():List<PlayListRaw>

    @GET("Detail/{id}")
    suspend fun getPlayDetails(@Path("id") id: String): PlayListDetailRaw

}
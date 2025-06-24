package com.vishalgupta.learntdd.di

import android.app.Activity
import androidx.activity.ComponentActivity
import com.vishalgupta.learntdd.MainActivityViewModel
import com.vishalgupta.learntdd.PlayListApi
import com.vishalgupta.learntdd.PlayListRepository
import com.vishalgupta.learntdd.PlayListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  Created by vishal.gupta on 25/06/25
 */
@Module
@InstallIn(dagger.hilt.components.SingletonComponent::class)
class PlayListModule {

//
//    @Provides
//    fun playListRepository(service:PlayListService): PlayListRepository {
//        return PlayListRepository(service)
//    }


    @Provides
    fun playListApi(retrofit: Retrofit): PlayListApi = retrofit.create(PlayListApi::class.java)

    @Provides
    fun retrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log everything
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

         val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3001/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

       return retrofit
    }
}
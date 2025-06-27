package com.vishalgupta.learntdd.helper

import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient

/**
 *  Created by vishal.gupta on 25/06/25
 */
object UITestHelper {
    val okHttpClient = OkHttpClient() // or inject your app's client
   private val idlingResourceOkhttp: OkHttp3IdlingResource = OkHttp3IdlingResource.create("okhttp", okHttpClient)
    val idlingResource : IdlingResource = idlingResourceOkhttp as IdlingResource
}
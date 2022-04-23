package com.example.libimgur

import com.example.libimgur.apis.ImgurAPIv3
import com.example.libimgur.converters.EnumConverterFactory
import okhttp3.OkHttpClient
import okhttp3.internal.cache.DiskLruCache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ImgurClient {
    public const val API_KEY = "" // TODO: add your API_KEY here
    // TODO: ideally should be in app not in library
    // according to our use case this behaves as a singleton, In big projects
    // we use dagger,hilton,coding kind of things for dependency injection
    //new instance only the first instance is required
    // when we actually require http client it will get executed(not when app launches)
    private val httpClient:OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor{
                val request = it.request().newBuilder().addHeader("Authorization",
                    "Client-ID $API_KEY"
                ).build()
                it.proceed(request)
            }
            .build()
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .baseUrl("https://api.imgur.com/3/").build()
    }
    val api:ImgurAPIv3 by lazy {
        retrofit.create(ImgurAPIv3::class.java)
    }
}
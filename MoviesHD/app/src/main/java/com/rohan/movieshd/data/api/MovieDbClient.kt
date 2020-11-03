package com.rohan.movieshd.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "ca19932c9becac7cff6dd591de9fd14e"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20


object MovieAPIClient {
    fun getClient(): MovieDbInterface {

        val requestInterceptor = Interceptor { chain ->
            val url: HttpUrl = chain.request()
                .url().newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request: Request = chain.request().newBuilder().url(url).build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDbInterface::class.java)
    }
}
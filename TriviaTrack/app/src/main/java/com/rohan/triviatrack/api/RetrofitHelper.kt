package com.rohan.triviatrack.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASEURL: String = "https://opentdb.com"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
package com.rohan.news27.data.network

import com.rohan.news27.data.model.NewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=3c306b27306041a2a1b8564dd048044c
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "3c306b27306041a2a1b8564dd048044c"
    ): Response<NewsDTO>


}
package com.rohan.triviatrack.api

import com.rohan.triviatrack.data_models.TriviaList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService {


    @GET("/api.php")
    suspend fun getTriviaQues(
        @Query("amount") amount: Int,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?,
        ): Response<TriviaList>

}
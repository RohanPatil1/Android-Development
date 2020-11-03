package com.rohan.movieshd.data.api

import com.rohan.movieshd.data.entity.MovieDetails
import com.rohan.movieshd.data.entity.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbInterface {

    //Observable is a data stream that does some work and emits data
    //Observer is the counter part of Observable,it receives the data emitted by the Observable

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>


    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int) :Single<MovieResponse>

}
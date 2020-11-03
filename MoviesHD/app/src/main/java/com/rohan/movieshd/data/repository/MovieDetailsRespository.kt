package com.rohan.movieshd.data.repository

import androidx.lifecycle.LiveData
import com.rohan.movieshd.data.api.MovieDbInterface
import com.rohan.movieshd.data.entity.MovieDetails
import io.reactivex.disposables.CompositeDisposable


class MovieDetailsRespository(private val apiService: MovieDbInterface) {

    lateinit var movieNetworkDataSource: MovieDetailsDataSource


    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieNetworkDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieNetworkDataSource.fetchMovieDetails(movieId)
        return movieNetworkDataSource.movieDetailsResponse
    }


    fun getNetworkState(): LiveData<NetworkState> {

        return movieNetworkDataSource.networkState
    }


}
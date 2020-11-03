package com.rohan.movieshd.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rohan.movieshd.data.entity.MovieDetails
import com.rohan.movieshd.data.repository.MovieDetailsRespository
import com.rohan.movieshd.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieRepository: MovieDetailsRespository, movieId: Int) :
    ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    // val movieDetails: LiveData<MovieDetails> = movieRepository.fetchMovieDetails(compositeDisposable, movieId)
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable, movieId)
    }


    val networkState:LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }


    //When Activity/Fragment gets Destroyed
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}
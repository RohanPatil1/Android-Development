package com.rohan.movieshd.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rohan.movieshd.data.entity.Movie

import com.rohan.movieshd.data.api.MovieDbInterface
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val moviesDataSource = MoviesDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(moviesDataSource)
        return moviesDataSource
    }


}
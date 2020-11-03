package com.rohan.movieshd.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rohan.movieshd.data.api.MovieDbInterface
import com.rohan.movieshd.data.entity.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsDataSource(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable,

    ) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState


    private val _movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieDetailsResponse: LiveData<MovieDetails> get() = _movieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        Log.d("ROHAN", "fetchMovieDetails: ROHAN")

        try {

            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            //SUCCESS
                            _movieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        }, {
                            //FAILED
                            _networkState.postValue(NetworkState.ERROR)
                            Log.d("ROHAN", "fetchMovieDetails: " + it.message.toString())
                        })
            )


        } catch (e: Exception) {
            Log.d("ROHAN", "fetchMovieDetails: " + e.message.toString())

        }


    }

}
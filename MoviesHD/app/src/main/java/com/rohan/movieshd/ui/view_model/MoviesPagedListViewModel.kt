package com.rohan.movieshd.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rohan.movieshd.data.entity.Movie
import com.rohan.movieshd.data.repository.MoviesPagedListRepository
import com.rohan.movieshd.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesPagedListViewModel(private val moviesPagedListRepository: MoviesPagedListRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val moviesPagedList: LiveData<PagedList<Movie>> by lazy {
        moviesPagedListRepository.fetchMovieList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        moviesPagedListRepository.getNetworkState()
    }

    fun isListEmpty(): Boolean {
        return moviesPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
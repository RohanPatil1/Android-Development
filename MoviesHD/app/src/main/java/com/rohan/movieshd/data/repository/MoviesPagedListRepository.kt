package com.rohan.movieshd.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.rohan.movieshd.data.api.MovieDbInterface
import com.rohan.movieshd.data.api.POST_PER_PAGE
import com.rohan.movieshd.data.entity.Movie
import io.reactivex.disposables.CompositeDisposable


//Repo for Main Activity
class MoviesPagedListRepository(private val apiService: MovieDbInterface) {


    lateinit var moviesPagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MoviesDataSourceFactory


    fun fetchMovieList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MoviesDataSourceFactory(apiService, compositeDisposable)

        //Config Paging
        val configPaging = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviesPagedList = LivePagedListBuilder(moviesDataSourceFactory, configPaging).build()
        return moviesPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {


        //Accessing moviesLiveDataSource (in FactoryClass) & transforming NetworkStateMutableLiveData to LiveData
        return Transformations.switchMap<MoviesDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MoviesDataSource::networkState
        )
    }

}
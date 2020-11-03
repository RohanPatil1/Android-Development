package com.rohan.movieshd.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.rohan.movieshd.R
import com.rohan.movieshd.data.api.MovieAPIClient
import com.rohan.movieshd.data.api.MovieDbInterface
import com.rohan.movieshd.data.repository.MovieDetailsRespository
import com.rohan.movieshd.data.repository.MoviesPagedListRepository
import com.rohan.movieshd.data.repository.NetworkState
import com.rohan.movieshd.ui.adapters.MoviesPagedListAdapter
import com.rohan.movieshd.ui.view_model.MovieViewModel
import com.rohan.movieshd.ui.view_model.MoviesPagedListViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.network_state_item.*

class MoviesPagedListActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesPagedListViewModel
    lateinit var moviesRepository: MoviesPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovieDbInterface = MovieAPIClient.getClient()
        moviesRepository = MoviesPagedListRepository(apiService)
        viewModel = getViewModel(moviesRepository)
        val moviesAdapter = MoviesPagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = moviesAdapter.getItemViewType(position)
                if (viewType == moviesAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3 //NETWORKVIEWTYPE will occupy all the 3 span

            }
        }


        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = moviesAdapter

        viewModel.moviesPagedList.observe(
            this, Observer {
                moviesAdapter.submitList(it)
            }
        )

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.isListEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.isListEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.isListEmpty()) {
                moviesAdapter.setNewNetworkState(it)
            }
        })


    }


    private fun getViewModel(

        repo: MoviesPagedListRepository
    ): MoviesPagedListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MoviesPagedListViewModel(repo) as T
            }
        })[MoviesPagedListViewModel::class.java]
    }
}
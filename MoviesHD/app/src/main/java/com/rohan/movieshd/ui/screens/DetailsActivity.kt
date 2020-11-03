package com.rohan.movieshd.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.rohan.movieshd.R
import com.rohan.movieshd.data.api.MovieAPIClient
import com.rohan.movieshd.data.api.MovieDbInterface
import com.rohan.movieshd.data.api.POSTER_BASE_URL
import com.rohan.movieshd.data.entity.MovieDetails
import com.rohan.movieshd.data.repository.MovieDetailsRespository
import com.rohan.movieshd.data.repository.NetworkState
import com.rohan.movieshd.ui.view_model.MovieViewModel
import kotlinx.android.synthetic.main.activity_details.*
import java.text.NumberFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel
    lateinit var repository: MovieDetailsRespository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieId: Int = intent.getIntExtra("id", 299534)

        val apiService: MovieDbInterface = MovieAPIClient.getClient()
        repository = MovieDetailsRespository(apiService)

        viewModel = getViewModel(movieId,repository)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADED) View.GONE else View.VISIBLE
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })


    }

    private fun bindUI(movie: MovieDetails) {
        movie_title.text = movie.title
        movie_tagline.text = movie.tagline
        movie_release_date.text = movie.releaseDate
        movie_rating.text = movie.rating.toString()
        movie_runtime.text = movie.runtime.toString() + " min"
        movie_overview.text = movie.overview


        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(movie.budget)
        movie_revenue.text = formatCurrency.format(movie.revenue)


        val posterUrl = POSTER_BASE_URL + movie.posterPath
        Glide.with(this).load(posterUrl).into(iv_movie_poster)


    }


    private fun getViewModel(movieId: Int,repo:MovieDetailsRespository): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieViewModel(repo, movieId) as T
            }
        })[MovieViewModel::class.java]
    }

}
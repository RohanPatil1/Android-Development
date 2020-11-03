package com.rohan.movieshd.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rohan.movieshd.R
import com.rohan.movieshd.data.api.POSTER_BASE_URL
import com.rohan.movieshd.data.entity.Movie
import com.rohan.movieshd.data.entity.MovieDetails
import com.rohan.movieshd.data.repository.NetworkState
import com.rohan.movieshd.ui.screens.DetailsActivity
import kotlinx.android.synthetic.main.activity_details.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class MoviesPagedListAdapter(public val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {


    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2
    var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bindUI(getItem(position), context)
        } else {
            (holder as NetworkStateViewHolder).bindUI(networkState)

        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    //+1 to show progress bar so itemCount+1
    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }

    }

    //Required For PagedListAdapter
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }


    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(movie: Movie?, context: Context) {
            itemView.cv_movie_title.text = movie?.title
            itemView.cv_movie_release_date.text = movie?.releaseDate

            val posterUrl: String = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context).load(posterUrl).into(itemView.cv_iv_movie_poster)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }


    class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindUI(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else if (networkState != null && networkState == NetworkState.EOLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else {
                itemView.error_msg_item.visibility = View.GONE
            }


        }

    }

    fun setNewNetworkState(newNetworkState: NetworkState) {
        val previousState: NetworkState? = this.networkState
        val hadExtraRow: Boolean = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow: Boolean = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount()) //RemoveThe Progress Bar
            } else {
                notifyItemInserted(super.getItemCount()) //Add The Progress Bar
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }


    }

}
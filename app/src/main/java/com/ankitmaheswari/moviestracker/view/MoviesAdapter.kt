package com.ankitmaheswari.moviestracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ankitmaheswari.moviestracker.R
import com.ankitmaheswari.moviestracker.model.MovieItem
import com.ankitmaheswari.moviestracker.utils.load

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class MoviesAdapter(
    private var moviesList: List<MovieItem>,
    private val movieItemClickListener: MovieItemClickListener
): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View,
                          private val movieItemClickListener: MovieItemClickListener)
        : RecyclerView.ViewHolder(itemView) {

        private val thumbnailIv: ImageView = itemView.findViewById(R.id.movie_thumbnail)
        private val titleTv: TextView = itemView.findViewById(R.id.movie_title)
        private val ratingTv: TextView = itemView.findViewById(R.id.movie_rating)
        private val playListTv: TextView = itemView.findViewById(R.id.movie_playlists)
        private val addButton: Button = itemView.findViewById(R.id.movie_add_to_playlist)

        fun bind(movieItem: MovieItem) {
            with(movieItem) {
                thumbnailIv.load(thumbnailUrl)
                titleTv.text = title
                ratingTv.text = ratingTv.resources.getString(R.string.movie_rating, rating)
                if (playLists.isEmpty()) {
                    playListTv.visibility = View.GONE
                } else {
                    playListTv.text = playListTv.resources.getString(
                        R.string.movie_playlists, playLists.toString())
                    playListTv.visibility = View.VISIBLE
                }
                addButton.setOnClickListener {
                    movieItemClickListener.onItemClick(movieItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view, movieItemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateData(updatedList: List<MovieItem>) {
        moviesList = updatedList
        notifyDataSetChanged()
    }
}
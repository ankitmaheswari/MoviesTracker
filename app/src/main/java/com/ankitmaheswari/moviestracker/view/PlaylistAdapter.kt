package com.ankitmaheswari.moviestracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ankitmaheswari.moviestracker.R
import com.ankitmaheswari.moviestracker.data.storage.PlayList

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class PlaylistAdapter(private var playListItems: List<PlayList>,
                      private val playListListener: PlayListListener)
    : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {


    class PlaylistViewHolder(itemView: View,
                             private val playListListener: PlayListListener)
        : RecyclerView.ViewHolder(itemView) {
        private val playlistNameTv: TextView = itemView.findViewById(R.id.play_list_name)

        fun bind(playList: PlayList) {
            playlistNameTv.text = playList.name
            itemView.setOnClickListener {
                playListListener.onItemClick(playList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_playlist, parent, false)
        return PlaylistViewHolder(view, playListListener)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playListItems[position])
    }

    override fun getItemCount(): Int {
        return playListItems.size
    }

    fun update(updatedList: List<PlayList>) {
        playListItems = updatedList
        notifyDataSetChanged()
    }
}
package com.ankitmaheswari.moviestracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankitmaheswari.moviestracker.R
import com.ankitmaheswari.moviestracker.data.storage.PlayList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Ankit Maheswari on 11/09/22.
 */
class PlayListBottomSheetDialogFragment private constructor(private val playLists: List<PlayList>,
                                                            private val playListListener: PlayListListener)
    : BottomSheetDialogFragment() {

    companion object {
        fun getInstance(playLists: List<PlayList>,
                        playListSelectListener: PlayListListener): PlayListBottomSheetDialogFragment {
            return PlayListBottomSheetDialogFragment(playLists, playListSelectListener)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_select_playlist, container)

        view.findViewById<View>(R.id.playlist_add).setOnClickListener {
            playListListener.addPlayList()
            dismiss()
        }
        setupPlayLists(view)
        return view
    }

    private fun setupPlayLists(view: View) {
        val playListAdapter = PlaylistAdapter(playLists, object : PlayListListener {
            override fun onItemClick(playList: PlayList) {
                playListListener.onItemClick(playList)
                dismiss()
            }

            override fun addPlayList() {
                playListListener.addPlayList()
            }

        })
        val playListRv = view.findViewById<RecyclerView>(R.id.playlist_list)
        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_list_item))
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        playListRv.layoutManager = layoutManager
        playListRv.adapter = playListAdapter
        playListRv.addItemDecoration(itemDecoration)
    }
}
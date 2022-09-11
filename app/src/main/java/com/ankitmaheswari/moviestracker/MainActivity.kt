package com.ankitmaheswari.moviestracker

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ankitmaheswari.moviestracker.data.storage.PlayList
import com.ankitmaheswari.moviestracker.databinding.ActivityMainBinding
import com.ankitmaheswari.moviestracker.model.MovieItem
import com.ankitmaheswari.moviestracker.utils.SHOW_ALL_LIST_ID
import com.ankitmaheswari.moviestracker.view.MovieItemClickListener
import com.ankitmaheswari.moviestracker.view.MoviesAdapter
import com.ankitmaheswari.moviestracker.view.PlayListBottomSheetDialogFragment
import com.ankitmaheswari.moviestracker.view.PlayListListener
import com.ankitmaheswari.moviestracker.viewmodel.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding
    private val moviesViewModel: MoviesViewModel by viewModels { viewModelFactory }
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.action_bar_movies_tracker)
        supportActionBar?.show()

        binding.fab.setOnClickListener { view ->
            showPlaylistFilter()
        }

        observeData()
        setupMoviesRecyclerView()
        moviesViewModel.getMovieItems()
    }

    private fun observeData() {
        moviesViewModel.getMovieItemsLiveData().observe(this) {
            Log.d("MainActivity", "movies: ${it.size}")
            moviesAdapter.updateData(updatedList = it)
        }
        moviesViewModel.getLoadingLiveData().observe(this) { visible ->
            binding.mainProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
            Log.d("MainActivity", "loading state: $visible")
        }
        moviesViewModel.getErrorLiveData().observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMoviesRecyclerView() {
        moviesAdapter = MoviesAdapter(listOf(), object : MovieItemClickListener {
            override fun onItemClick(movieItem: MovieItem) {
                addToPlayList(movieItem)
            }
        })
        val recyclerView = findViewById<RecyclerView>(R.id.movies_list)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.adapter = moviesAdapter
    }

    private fun addToPlayList(movieItem: MovieItem) {
        moviesViewModel.getPlayLists().observe(this) { playLists ->
            showPlaylistSelector(movieItem, playLists)
        }
    }

    private fun showPlaylistSelector(movieItem: MovieItem, playlists: List<PlayList>) {
        val playlistBottomSheet = PlayListBottomSheetDialogFragment.getInstance(playlists,
            object : PlayListListener {
                override fun onItemClick(playList: PlayList) {
                    moviesViewModel.addMovieToPlayList(movieItem, playList)
                }

                override fun addPlayList() {
                    showAddPlayListDialog()
                }
            })
        playlistBottomSheet.show(supportFragmentManager, "select-playlist")
    }

    private fun showAddPlayListDialog() {
        if (!isFinishing) {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_playlist)
            val input = dialog.findViewById<EditText>(R.id.add_playlist_input)
            val addButton = dialog.findViewById<TextView>(R.id.add_playlist_button)
            addButton.setOnClickListener {
                val name = input.text
                if (name.isNotBlank()) {
                    addPlayList(name.toString())
                }
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun addPlayList(name: String) {
        moviesViewModel.addPlayList(name).observe(this) { success ->
            if (success) {
                Toast.makeText(this, getString(R.string.add_playlist_success), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPlaylistFilter() {
        moviesViewModel.getPlayLists().observe(this) { playlists ->
            if (!isFinishing) {
                val lists: MutableList<PlayList> = mutableListOf()
                lists.addAll(playlists)
                lists.add(PlayList(id = SHOW_ALL_LIST_ID, "Show All"))

                val playlistBottomSheet = PlayListBottomSheetDialogFragment.getInstance(lists,
                    object : PlayListListener {
                        override fun onItemClick(playList: PlayList) {
                            if (playList.id == SHOW_ALL_LIST_ID) {
                                moviesViewModel.getMovieItems()
                            } else {
                                moviesViewModel.filterMovies(playList)
                            }
                        }

                        override fun addPlayList() {
                            showAddPlayListDialog()
                        }
                    })
                playlistBottomSheet.show(supportFragmentManager, "select-playlist")
            }
        }
    }
}
package com.ncode.muplayer.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ncode.muplayer.R
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.viewModel.PlayerViewModel
import kotlinx.android.synthetic.main.fragment_music_player.*
import kotlinx.android.synthetic.main.song_card_view.*


class MusicPlayerFragment : Fragment() {

    private lateinit var playerViewModel : PlayerViewModel
    private var position : Int = 0

    private val TAG = "pos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        position = arguments?.getInt("position")!!
        Log.i(TAG, position.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerViewModel = ViewModelProvider(this).get(PlayerViewModel :: class.java)
        playerViewModel.allSongs.observe(viewLifecycleOwner, {songs ->
            songs.let { getAllSongsList(it) }
        })
    }

    private fun getAllSongsList(songsPlayList : List<MusicPlayerModel>) {
        Log.i(TAG, "getAllSongsList: ${songsPlayList.size}")
        val currentSong = songsPlayList[position]
        player_song_name.text = currentSong.songName
        player_artist_name.text = currentSong.artistInfo
    }

}
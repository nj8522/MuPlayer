package com.ncode.muplayer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ncode.muplayer.R
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.presenter.MediaPlayerPresenter
import com.ncode.muplayer.services.MediaPlayerServices
import kotlinx.android.synthetic.main.fragment_music_player.*
import java.lang.Exception


class MusicPlayerFragment : Fragment() {

    private val TAG = "pos"

    private lateinit var mediaTackPath : String
    private var position : Int = 0

    //Services
    var mediaService : MediaPlayerServices? = null
    var isBounded = false
    private var playPauseAction = false


    //Song Shelf
    private var musicRack : List<MusicPlayerModel> = listOf()

    //Presenter
    lateinit var mediaPlayerPresenter: MediaPlayerPresenter

    //UI
    private lateinit var playPauseButton : Button
    private lateinit var playerSeekBar: SeekBar
    private lateinit var songName : TextView
    private lateinit var artistName : TextView

    private val setUpConnectionWithService = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MediaPlayerServices.MediaPlayerBinder
            mediaService = binder.getBinderData()
            isBounded = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBounded = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        position = arguments?.getInt("position")!!
        Log.i(TAG, position.toString())

       mediaPlayerPresenter = MediaPlayerPresenter(context!!)
       musicRack = mediaPlayerPresenter.getAllSongs()


        val intent = Intent(context, MediaPlayerServices :: class.java)
        playPauseAction = activity!!.bindService(intent, setUpConnectionWithService, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //UI
        playPauseButton = view.findViewById(R.id.play_pause_button)
        playerSeekBar = view.findViewById(R.id.seekBar)
        songName = view.findViewById(R.id.player_song_name)
        artistName = view.findViewById(R.id.player_artist_name)


        //Action
        playPauseButton.setOnClickListener {
            changeStateOfThePlayer()
        }

        skip_previous.setOnClickListener {
            playPreviousSong()
        }

        skip_next.setOnClickListener {
            playNextSong()
        }

         getAllSongsList(musicRack)
    }

    private fun getAllSongsList(songsPlayList : List<MusicPlayerModel>) {

        //musicRack  = songsPlayList
        val currentSong = songsPlayList[position]
        Log.i(TAG, "getAllSongsList: ${currentSong.songName}")

        songName.text = currentSong.songName
        artistName.text = currentSong.artistInfo
        mediaTackPath = currentSong.path
    }

    private fun tackCurrentSong() {}

    private fun changeStateOfThePlayer() {

        if(playPauseAction) {
            playPauseAction = false
            playPauseButton.setBackgroundResource(R.drawable.pause_button)
            mediaService?.playMusic(mediaTackPath)
            activity?.startService(startMusicPlayerService())

        } else {
            playPauseAction = true
            playPauseButton.setBackgroundResource(R.drawable.play_button)
            mediaService?.pauseMusic()
        }

        initializeSeekBar()
        trackSeekBar()
    }

    private fun playPreviousSong() {
        Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show()
    }

    private fun playNextSong() {
        Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
    }

    private fun trackSeekBar() {
        playerSeekBar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaService?.mediaPlayerSeekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initializeSeekBar() {

        val mediaPlayer = mediaService!!.trackSeekBar()
        playerSeekBar.max = mediaPlayer.duration


        val handler = Handler()
        handler.postDelayed(object  : Runnable{
            override fun run() {
                try {
                   playerSeekBar.progress = mediaPlayer.currentPosition
                   handler.postDelayed(this, 100)
                }catch (e : Exception) {
                   playerSeekBar.progress = 0
                }
            }
        },0)
    }

    private fun startMusicPlayerService() : Intent {
        val serviceIntent = Intent(context, MediaPlayerServices :: class.java)
        return serviceIntent
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(setUpConnectionWithService)
        isBounded = false
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.stopService(startMusicPlayerService())
    }

}
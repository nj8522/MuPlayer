package com.ncode.muplayer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
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
import com.ncode.muplayer.R
import com.ncode.muplayer.helper.SongFetchHelper
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.presenter.MediaPlayerPresenter
import com.ncode.muplayer.services.MediaPlayerServices
import kotlinx.android.synthetic.main.fragment_music_player.*
import java.lang.Exception
import java.util.*


class MusicPlayerFragment : Fragment() {

    private lateinit var mediaTackPath : String
    private var songPosition : Int = 0

    //Services
    var mediaService : MediaPlayerServices? = null
    var isBounded = false

    //Song Shelf
    private var musicRack : List<MusicPlayerModel> = listOf()

    //Presenter
    private lateinit var mediaPlayerPresenter : MediaPlayerPresenter

    //UI
    private lateinit var playPauseButton : Button
    private lateinit var playerSeekBar: SeekBar
    private lateinit var songName : TextView
    private lateinit var artistName : TextView

    //Seek Position
    private var mediaPlayerPosition = 0

    //Timer
    private val timer = Timer()

    //Set Up Connection With Service
    private val setUpConnectionWithService = object : ServiceConnection {
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

        songPosition = arguments?.getInt("position")!!
        Log.i(TAG, "$songPosition")

        val songData = SongFetchHelper().fetchSongForTheUser(songPosition, context!!)
        Log.i(TAG, "onCreate: ${songData.songName} ${songData.path}")


       mediaPlayerPresenter = MediaPlayerPresenter(context!!)
       musicRack = mediaPlayerPresenter.getAllSongs()

        activity?.startService(startMusicPlayerService())

        val intent = Intent(context, MediaPlayerServices :: class.java)
        activity!!.bindService(intent, setUpConnectionWithService, Context.BIND_AUTO_CREATE)
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

        val currentSong = songsPlayList[songPosition]
        Log.i(TAG, "getAllSongsList: ${currentSong.songName}")

        songName.text = currentSong.songName
        artistName.text = currentSong.artistInfo
        mediaTackPath = currentSong.path
    }


    private fun changeStateOfThePlayer() {

        if(mediaService!!.mediaPlayerStatus()) {

            playPauseButton.setBackgroundResource(R.drawable.play_button)
            mediaService?.pauseMusic()

            mediaPlayerPosition = mediaService!!.trackSeekBar()

        } else {

            playPauseButton.setBackgroundResource(R.drawable.pause_button)
            sendMediaBroadCast()
            mediaService?.playMusic(mediaTackPath)
            playerSeekBar.max = mediaService!!.trackMaxLength()
        }

        initializeSeekBar()
        trackSeekBar()
    }

    private fun sendMediaBroadCast() {

        try {

            val current = musicRack[songPosition]
            val songName = current.songName
            val artistName = current.artistInfo

            val mediaIntent = Intent().apply {
                putExtra("song", songName)
                putExtra("artist", artistName)
            }

            mediaIntent.action = MEDIA_TO_SERVICE
            context?.sendBroadcast(mediaIntent)

        } catch ( e : Exception) {
            Log.i(TAG, "sendMediaBroadCast: ${e.message}")
        }
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


        if(mediaService!!.isPlaying) {

            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    Log.i(TAG, "run: ${mediaService!!.getCurrentPosition()}")
                    playerSeekBar.progress = mediaService!!.getCurrentPosition()
                }
            }, 0, 100)
        }
    }

    private fun startMusicPlayerService() : Intent {
        return Intent(context, MediaPlayerServices :: class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        activity?.stopService(startMusicPlayerService())
    }

    companion object {
        private const val TAG = "pos"
        private const val MEDIA_TO_SERVICE = "WIDGET_DATA"
    }

}
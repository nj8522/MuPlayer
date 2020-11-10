package com.ncode.muplayer.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.*
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ncode.muplayer.MediaPLayerWidget
import com.ncode.muplayer.R
import com.ncode.muplayer.ui.MusicPlayerFragment

class MediaPlayerServices : Service() {

    private val TAG = "service_media"

    private val playerBinder = MediaPlayerBinder()

    private lateinit var mediaPlayer: MediaPlayer

    private val MEDIA_TO_SERVICE = "WIDGET_DATA"

    //Channel
    val CHANNEL_ID = "MuPlayer_Channel"

    //Notification
    var notification: Notification? = null

    private var songName = ""
    private var artist = ""

    //Media Player Status
    var isPlaying = false
    var wasPlaying = false
    var mediaPlayerPosition = 0


    private val mediaReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            songName = intent?.extras?.getString("song")!!
            artist = intent.extras?.getString("artist")!!
            Log.i(TAG, "onReceive: $songName, $artist")
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        registerBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        val filter = IntentFilter(MEDIA_TO_SERVICE)
        registerReceiver(mediaReceiver, filter)
        Log.i(TAG, "onReceive: Intent Filter Done")
    }


    override fun onBind(intent: Intent?): IBinder? {
        return playerBinder
    }


    fun playMusic(songPath: String) {

        Log.i(TAG, "playMusic: playing")

        try {

            if (wasPlaying) {
                mediaPlayer.seekTo(mediaPlayerPosition)
                mediaPlayer.start()
                wasPlaying = false
            } else {
                mediaPlayer.setDataSource(songPath)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }

            isPlaying = true
            controlMusicUsingWidget()

        } catch (e: Exception) {
            mediaPlayer.stop()
            isPlaying = false
        }
    }


    fun pauseMusic() {

        if (mediaPlayer.isPlaying) {
            mediaPlayerPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()
            wasPlaying = true
            isPlaying = false
        }

        controlMusicUsingWidget()
    }

    fun mediaPlayerSeekTo(position: Int) {
        mediaPlayer.seekTo(position)
        mediaPlayerPosition = position
    }

    fun trackSeekBar(): Int {
        Log.i(TAG, "trackSeekBar: ${mediaPlayer.currentPosition}")
        return mediaPlayer.currentPosition
    }

    fun trackMaxLength(): Int {
        return mediaPlayer.duration
    }

    fun mediaPlayerStatus(): Boolean {
        if (mediaPlayer.isPlaying) {
            return true
        }
        return false
    }

    private fun playPreviousMusic(): PendingIntent? {
        return null
    }

    private fun playNextMusic(): PendingIntent? {
        return null
    }

    private fun playCurrentMusic(): PendingIntent? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        /*notification = buildNavigationNotifier()
        startForeground(1245, notification)*/
        return START_STICKY
    }

    private fun controlMusicUsingWidget() {

        val view = RemoteViews(packageName, R.layout.media_p_layer_widget)

        if (isPlaying) {

            view.setTextViewText(R.id.widget_song_title, songName)
            view.setTextViewText(R.id.widget_song_artist, artist)
            view.setImageViewResource(R.id.widget_play_pause, R.drawable.pause_button_image)
        } else {
            view.setTextViewText(R.id.widget_song_title, "")
            view.setTextViewText(R.id.widget_song_artist, "")
            view.setImageViewResource(R.id.widget_play_pause, R.drawable.play_arrow)
        }

        val mediaPlayerWidget = ComponentName(this, MediaPLayerWidget::class.java)
        val manger = AppWidgetManager.getInstance(this)
        manger.updateAppWidget(mediaPlayerWidget, view)
    }


    private fun buildNavigationNotifier(): Notification? {

        val pendingIntent =
            Intent(this, MusicPlayerFragment::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.notification_icon_media)
            .addAction(R.drawable.notification_previous_media, "Previous", playPreviousMusic())
            .addAction(R.drawable.notification_play_media, "Play", playCurrentMusic())
            .addAction(R.drawable.notification_next_media, "Next", playNextMusic())
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1 /* #1: pause button \*/)
            )
            .setContentTitle("MuPlayer")
            .setContentText("Playing")
            .setContentIntent(pendingIntent)
            .build()

        return notification
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        unregisterReceiver(mediaReceiver)
    }

    inner class MediaPlayerBinder : Binder() {
        fun getBinderData(): MediaPlayerServices {
            return this@MediaPlayerServices
        }
    }
}
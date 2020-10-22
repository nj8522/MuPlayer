package com.ncode.muplayer.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ncode.muplayer.R
import com.ncode.muplayer.foreground.NotificationChannelForMuPlayer
import com.ncode.muplayer.ui.MusicPlayerFragment

class MediaPlayerServices : Service() {

    private val TAG = "media"

    private val playerBinder = MediaPlayerBinder()

    lateinit var mediaPlayer : MediaPlayer

    //Channel
    val CHANNEL_ID = "MuPlayer_Channel"


    //Notification
    var notification: Notification? = null

    override fun onBind(intent: Intent?): IBinder? {
        return playerBinder
     }


    fun playMusic(songPath : String) {

       Log.i(TAG, "playMusic: playing")

       try {
           mediaPlayer = MediaPlayer()
           mediaPlayer.setDataSource(songPath)
           mediaPlayer.prepare()
           mediaPlayer.start()
       } catch (e : Exception) {
           mediaPlayer.stop()
       }
   }

    fun pauseMusic() {

       if(mediaPlayer.isPlaying) {
           mediaPlayer.pause()

       }

   }

    fun mediaPlayerSeekTo(position : Int) {
        mediaPlayer.seekTo(position)
    }

    fun trackSeekBar() : MediaPlayer{
     return  mediaPlayer
   }

    private fun playPreviousMusic(): PendingIntent? { return null}

    private fun playNextMusic() : PendingIntent? { return null }

    private fun playCurrentMusic() : PendingIntent? { return null }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        notification = buildNavigationNotifier()
        startForeground(1245, notification)

        return START_NOT_STICKY
    }

    private fun buildPendingIntent() : PendingIntent{

        return Intent(this, MusicPlayerFragment :: class.java).let { notificationIntent ->
             PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }
    }

    private fun buildNavigationNotifier() : Notification? {

        notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.notification_icon_media)
            .addAction(R.drawable.notification_previous_media, "Previous", playPreviousMusic())
            .addAction(R.drawable.notification_play_media, "Play", playCurrentMusic())
            .addAction(R.drawable.notification_next_media, "Next", playNextMusic())
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1 /* #1: pause button \*/))
            .setContentTitle("MuPlayer")
            .setContentText("Playing")
            .setContentIntent(buildPendingIntent())
            .build()

      return notification
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    inner class MediaPlayerBinder : Binder() {
        fun getBinderData() : MediaPlayerServices{
            return this@MediaPlayerServices
        }
    }
}
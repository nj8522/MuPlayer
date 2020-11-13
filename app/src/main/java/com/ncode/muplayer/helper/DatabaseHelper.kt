package com.ncode.muplayer.helper

import android.content.Context
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.presenter.MediaPlayerPresenter


class DatabaseHelper {


    fun getTheListOfSongs(position: Int, context: Context): MusicPlayerModel {

        val songsList = MediaPlayerPresenter(context).getAllSongs()

        if (position >= 0 && position <= songsList.size) {
            return VerifySong().verifyingSongsCredentials(songsList, position)

        } else {
            throw ArrayIndexOutOfBoundsException("Songs are Not Available At current Position")
        }
    }
}


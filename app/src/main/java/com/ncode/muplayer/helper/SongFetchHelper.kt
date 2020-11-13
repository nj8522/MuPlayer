package com.ncode.muplayer.helper

import android.content.Context
import com.ncode.muplayer.models.MusicPlayerModel
import java.lang.IllegalArgumentException

class SongFetchHelper {

    fun fetchSongForTheUser(position : Int, context: Context) : MusicPlayerModel {

        if(position >= 0) {
            return DatabaseHelper().getTheListOfSongs(position, context)
        } else {
           throw IllegalArgumentException("This is Not a valid Position")
       }
    }
}
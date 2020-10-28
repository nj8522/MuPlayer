package com.ncode.muplayer.providertoobject

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ncode.muplayer.contract.MediaPlayerContract
import com.ncode.muplayer.crud.CrudOperation
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.provider.MediaPlayerContentDatabase
import com.ncode.muplayer.provider.MediaPlayerProvider
import java.lang.Exception
import java.security.AccessControlContext
import kotlin.math.log

class ConvertProviderDataToObjects(var context: Context) {


    val SONG_NAME = "Song"
    val ARTIST_NAME = "Artist"
    val ALBUM_NAME = "Album"
    val PATH = "path"

    val contentValue = ContentValues()

    fun convertToObject(songsDataList: MutableList<MusicPlayerModel>) {

        val resolver = context.contentResolver

        try {

            songsDataList.forEach {current ->

                contentValue.put(SONG_NAME, current.songName)
                contentValue.put(ARTIST_NAME, current.artistInfo)
                contentValue.put(ALBUM_NAME, current.albumInfo)
                contentValue.put(PATH, current.path)
                Log.i("object", "${current.songName} ${current.albumInfo}")
                resolver.insert(MediaPlayerContentDatabase.CONTENT_URI, contentValue)
            }

        } catch (e : Exception) {
            Log.i("content", e.message.toString())
        }

        Log.i("content", "Done")
    }
}
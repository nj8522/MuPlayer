package com.ncode.muplayer.provider

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.ncode.muplayer.contract.MediaPlayerContract
import com.ncode.muplayer.models.MusicPlayerModel
import java.lang.Exception
import kotlin.math.log


class MediaPlayerProvider(var context: Context)  {

    private val TAG = "cursor"


    fun songsFromPhoneProvider() : MutableList<MusicPlayerModel> {

        val songListDataFromProvider = mutableListOf<MusicPlayerModel>()

        val resolver : ContentResolver = context.contentResolver

        val music = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC +"!= 0"
        //val projection = arrayOf(MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST)
        var cursor : Cursor? = null
        try {
            cursor = resolver.query(music, null, selection, null, null)
        } catch (e : Exception) {
            Log.i(TAG, "retrieveSongFromProvider: ${e.message.toString()}")
        }


        if(cursor!!.moveToFirst()) {

            do {

                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val album = cursor.getString( cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                Log.i("provider", "$name, $path")

                songListDataFromProvider.add(MusicPlayerModel(name, artist, album, path))

            } while (cursor.moveToNext())
        }

        cursor.close()

        return  songListDataFromProvider
     }








}
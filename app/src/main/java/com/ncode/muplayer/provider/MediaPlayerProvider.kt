package com.ncode.muplayer.provider

import android.app.Application
import android.content.ContentResolver
import android.provider.MediaStore
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.viewModel.PlayerViewModel

class MediaPlayerProvider {



    fun retrieveSongFromProvider(application: Application, playerViewModel: PlayerViewModel) {

        val resolver : ContentResolver = application.contentResolver

        val music = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC +"!= 0"
        val projection = arrayOf(MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST)
        val cursor = resolver.query(music, null, selection, null, null)

        if(cursor!!.moveToFirst()) {

            do {

                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val album = cursor.getString( cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                playerViewModel.insert(MusicPlayerModel(null, name, artist, album, path))

            } while (cursor.moveToNext())
        }

        cursor.close()
     }








}
package com.ncode.muplayer.helper

import com.ncode.muplayer.models.MusicPlayerModel
import java.lang.Exception
import java.text.FieldPosition

class VerifySong {


     fun verifyingSongsCredentials(songsList : MutableList<MusicPlayerModel>, position: Int) : MusicPlayerModel {

         val current = songsList[position]
         var songName = ""
         var artists  = ""
         var albumInfo = ""
         var path = ""

        if(!current.songName.isNullOrBlank()) {
            songName = current.songName.toString()
        }

         if(!current.artistInfo.isNullOrBlank()) {
             artists = current.artistInfo.toString()
         }

         if(!current.albumInfo.isNullOrBlank()) {
             albumInfo = current.albumInfo.toString()
         }

         if(!current.path.isBlank()) {
             path = current.path
         } else {
             throw Exception("Current Song Path Not Found")
         }

         return MusicPlayerModel(songName, artists, albumInfo, path)
     }
}
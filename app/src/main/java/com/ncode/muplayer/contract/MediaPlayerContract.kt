package com.ncode.muplayer.contract

import android.content.Context
import com.ncode.muplayer.crud.CrudOperation
import com.ncode.muplayer.models.MusicPlayerModel


interface MediaPlayerContract {

     interface PlayerView {
         fun view()
     }

    interface PlayerModel {
        fun model()
    }

    interface PlayerPresenter {

        fun getSize() : Boolean
        fun getSongFromProvider()
        fun getAllSongs() : MutableList<MusicPlayerModel>
    }

    interface PlayerQuery {
        fun databaseQuery(): MutableList<MusicPlayerModel>
        fun getTotalNumberOfSongs() : Int
    }

    interface ConvertDataToObjects {
        fun convertToObject(songsDataList : MutableList<MusicPlayerModel>, context: Context)
    }

    interface DataFromProvider {
        fun retrieveSongFromProvider(context: Context) : MutableList<MusicPlayerModel>
    }



}
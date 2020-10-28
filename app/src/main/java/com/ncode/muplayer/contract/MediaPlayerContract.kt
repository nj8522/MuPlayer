package com.ncode.muplayer.contract

import android.content.Context
import com.ncode.muplayer.crud.CrudOperation
import com.ncode.muplayer.models.MusicPlayerModel


interface MediaPlayerContract {

    interface PlayerPresenter {

        fun getSize() : Boolean
        fun getAllSongs() : MutableList<MusicPlayerModel>
        fun retrieveSongFromProvider()
    }

    interface MediaPlayerView {
        fun initView()
        fun updateAdapter()
    }

 }
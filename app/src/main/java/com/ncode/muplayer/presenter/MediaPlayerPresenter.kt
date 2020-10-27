package com.ncode.muplayer.presenter

import android.content.Context
import android.util.Log
import com.ncode.muplayer.contract.MediaPlayerContract
import com.ncode.muplayer.crud.CrudOperation
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.provider.MediaPlayerProvider
import com.ncode.muplayer.providertoobject.ConvertProviderDataToObjects
import java.lang.Exception

class MediaPlayerPresenter(val context : Context) :  MediaPlayerContract.PlayerPresenter{

    private val query : MediaPlayerContract.PlayerQuery = CrudOperation(context)
    private val mediaPlayerProvider : MediaPlayerContract.DataFromProvider = MediaPlayerProvider()
    private val convertDataToObjects : MediaPlayerContract.ConvertDataToObjects = ConvertProviderDataToObjects()

    private val TAG = "size"

    override fun getSongFromProvider() {
        val songList = mediaPlayerProvider.retrieveSongFromProvider(context)
        convertDataToObjects.convertToObject(songList, context)
    }

    override fun getAllSongs() = query.databaseQuery()

    override fun getSize() : Boolean {
      
      var songSize = 0
        try {
            songSize = query.getTotalNumberOfSongs()
            Log.i("content", songSize.toString())

        } catch (e : Exception) {
            Log.i(TAG, "getSize: ${e.message.toString()}")
        }
        
        if(songSize > 0) {
            return true
        }
        return false
    }
}
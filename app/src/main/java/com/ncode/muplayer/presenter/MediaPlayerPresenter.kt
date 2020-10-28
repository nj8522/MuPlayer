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


    private val query  = CrudOperation(context)
    private val mediaPlayerProvider = MediaPlayerProvider(context)
    private val convertDataToObjects = ConvertProviderDataToObjects(context)


    private val TAG = "size"



    override fun retrieveSongFromProvider() {
        val providerData = mediaPlayerProvider.songsFromPhoneProvider()
        convertDataToObjects.convertToObject(providerData)
    }

    override fun getAllSongs() = query.mediaQuery()

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
package com.ncode.muplayer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ncode.muplayer.database.MusicPlayerRoomDatabase
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.provider.MediaPlayerProvider
import com.ncode.muplayer.repository.MusicPlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PlayerViewModel (application: Application) : AndroidViewModel(application) {

        val repository : MusicPlayerRepository
        val allSongs : LiveData<List<MusicPlayerModel>>
        private val context = application

    init {
        val playerDao = MusicPlayerRoomDatabase.getDataBase(application).musicPlayerDao()
        repository = MusicPlayerRepository(playerDao)
        allSongs = repository.allSongs
    }

    fun insert(musicPlayerModel: MusicPlayerModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(musicPlayerModel)
    }


    fun getAllSongsFromProvider() {
        val mediaPlayerProvider = MediaPlayerProvider()
        mediaPlayerProvider.retrieveSongFromProvider(context, this)
    }

}
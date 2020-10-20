package com.ncode.muplayer.repository

import androidx.lifecycle.LiveData
import com.ncode.muplayer.dao.MusicPlayerDao
import com.ncode.muplayer.models.MusicPlayerModel

class MusicPlayerRepository (private val musicPlayerDao : MusicPlayerDao) {

     val allSongs : LiveData<List<MusicPlayerModel>> = musicPlayerDao.getAllSongs()

    suspend fun insert(musicPlayerModel: MusicPlayerModel) {
        musicPlayerDao.insert(musicPlayerModel)
    }

}
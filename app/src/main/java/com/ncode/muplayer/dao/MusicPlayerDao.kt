package com.ncode.muplayer.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ncode.muplayer.models.MusicPlayerModel

@Dao
interface MusicPlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(musicPlayerModel: MusicPlayerModel)

    @Query("SELECT * FROM MusicPlayerTable ORDER BY id ASC")
    fun getAllSongs(): LiveData<List<MusicPlayerModel>>


}
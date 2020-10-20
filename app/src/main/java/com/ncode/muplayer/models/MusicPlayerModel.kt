package com.ncode.muplayer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MusicPlayerTable")
class MusicPlayerModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int?,

    @ColumnInfo(name = "Song name")
    val songName: String,

    @ColumnInfo(name = "Artist Name")
    val artistInfo: String,

    @ColumnInfo(name = "Album")
    val albumInfo: String,

    @ColumnInfo(name = "Path")
    val path: String
)





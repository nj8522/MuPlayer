package com.ncode.muplayer.database

import android.provider.BaseColumns

object PlayerDbContract {

    const val MEDIA_PLAYER_CREATE_TABLE = "CREATE TABLE ${MediaPlayerEntry.TABLE_NAME} (" +
            "${MediaPlayerEntry.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${MediaPlayerEntry.SONG_NAME} TEXT," +
            "${MediaPlayerEntry.ARTIST_NAME} TEXT," +
            "${MediaPlayerEntry.ALBUM_NAME} TEXT," +
            "${MediaPlayerEntry.PATH} TEXT)"


    const val MEDIA_PLAYER_DROP_TABLE = "DROP TABLE IF EXISTS ${MediaPlayerEntry.TABLE_NAME}"

    object MediaPlayerEntry : BaseColumns {

          const val TABLE_NAME = "Player"
          const val ID = "_id"
          const val SONG_NAME = "Song"
          const val ARTIST_NAME = "Artist"
          const val ALBUM_NAME = "Album"
          const val PATH = "path"
    }

}
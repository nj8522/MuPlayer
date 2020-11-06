package com.ncode.muplayer.crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ncode.muplayer.contract.MediaPlayerContract
import com.ncode.muplayer.database.PlayerDbContract
import com.ncode.muplayer.database.MuPlayerDatabase
import com.ncode.muplayer.models.MusicPlayerModel
import com.ncode.muplayer.provider.MediaPlayerContentDatabase
import java.lang.Exception

class CrudOperation (val context : Context) {


    fun mediaQuery(): MutableList<MusicPlayerModel> {

        val allSongsDataFromLocalDb : MutableList<MusicPlayerModel> = mutableListOf()

        var cursor : Cursor? = null

            try {

                cursor = context.contentResolver.query(MediaPlayerContentDatabase.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)


            } catch (e : Exception) {
                return allSongsDataFromLocalDb
            }



        Log.i("content", cursor!!.count.toString())

       cursor.moveToFirst()

        try {

            do  {

                val song = cursor.getString(cursor.getColumnIndex(PlayerDbContract.MediaPlayerEntry.SONG_NAME))
                val artist = cursor.getString(cursor.getColumnIndex(PlayerDbContract.MediaPlayerEntry.ARTIST_NAME))
                val album = cursor.getString(cursor.getColumnIndex(PlayerDbContract.MediaPlayerEntry.ALBUM_NAME))
                val path = cursor.getString(cursor.getColumnIndex(PlayerDbContract.MediaPlayerEntry.PATH))

                allSongsDataFromLocalDb.add(MusicPlayerModel(song, artist, album, path))
            } while (cursor.moveToNext())

        } catch (e : Exception) {
            Log.i("content", e.message.toString())
        }

        val pos = allSongsDataFromLocalDb[1]
        val name = pos.path

        Log.i("content", name)
        cursor.close()

        return allSongsDataFromLocalDb

    }

    fun getTotalNumberOfSongs(): Int {

        val cursor : Cursor? = context.contentResolver.query(MediaPlayerContentDatabase.CONTENT_URI,
            null,
            null,
            null,
            null)

        return cursor!!.count

    }


}
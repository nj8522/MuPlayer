package com.ncode.muplayer.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log
import com.ncode.muplayer.database.MuPlayerDatabase
import com.ncode.muplayer.database.PlayerDbContract

class MediaPlayerContentDatabase : ContentProvider() {

   lateinit var db : SQLiteDatabase

   companion object {
       const val AUTHORITY = "com.media.muplayer.provider"
       val CONTENT_URI = Uri.parse("content://${AUTHORITY}")!!
       val uriMatcher : UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
   }

     private val PLAYER = 1
     private val PLAYER_ID = 2

     init {
        uriMatcher.addURI(AUTHORITY, PlayerDbContract.MediaPlayerEntry.TABLE_NAME, PLAYER)
        uriMatcher.addURI(AUTHORITY, "${PlayerDbContract.MediaPlayerEntry.TABLE_NAME}/#", PLAYER_ID)
     }



    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        val row = db.insert(PlayerDbContract.MediaPlayerEntry.TABLE_NAME, null, values)
        ContentUris.withAppendedId(CONTENT_URI, row)
        context!!.contentResolver.notifyChange(uri, null)
        return  uri
    }

    override fun onCreate(): Boolean {

        val muPlayerDataBase = MuPlayerDatabase(context!!)
        db = muPlayerDataBase.writableDatabase
       return  db != null

    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val myQuery = SQLiteQueryBuilder()
        myQuery.tables = PlayerDbContract.MediaPlayerEntry.TABLE_NAME

        val cursor : Cursor = myQuery.query(db, null, null, null, null, null, null)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        Log.i("provider", cursor.count.toString())
        return  cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
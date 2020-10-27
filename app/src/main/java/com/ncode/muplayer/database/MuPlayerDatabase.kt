package com.ncode.muplayer.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MuPlayerDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VER) {

    companion object {
        const val DATABASE_NAME = "Player.db"
        const val VER = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(PlayerDbContract.MEDIA_PLAYER_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PlayerDbContract.MEDIA_PLAYER_DROP_TABLE)
    }


 }
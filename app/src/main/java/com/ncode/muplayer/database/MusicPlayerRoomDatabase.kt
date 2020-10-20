package com.ncode.muplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ncode.muplayer.dao.MusicPlayerDao
import com.ncode.muplayer.models.MusicPlayerModel

@Database(entities = [MusicPlayerModel::class], version = 1, exportSchema = false)
abstract class MusicPlayerRoomDatabase : RoomDatabase() {


     abstract fun musicPlayerDao() : MusicPlayerDao

    companion object {

        @Volatile
        private var INSTANCE : MusicPlayerRoomDatabase? = null

        fun getDataBase(context: Context) : MusicPlayerRoomDatabase {

            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance

            } else {

               synchronized(this) {

                   val instance = Room.databaseBuilder(
                       context.applicationContext,
                       MusicPlayerRoomDatabase :: class.java,
                       "PlayerDataBase"
                   ).build()

                  INSTANCE = instance
                  return  instance
               }
            }
        }
    }
}
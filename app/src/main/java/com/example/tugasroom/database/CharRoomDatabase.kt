package com.example.tugasroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharEntity::class], version = 1, exportSchema = false)
abstract class CharRoomDatabase : RoomDatabase() {
    abstract fun charDao(): CharDao?

    companion object {
        @Volatile
        private var INSTANCE: CharRoomDatabase? = null

        fun getDatabase(context: Context): CharRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(CharRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CharRoomDatabase::class.java,
                        "char_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
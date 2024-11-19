package com.example.tugasroom.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(charEntity: CharEntity)

    @Delete
    fun delete(charEntity: CharEntity)

    @Query("SELECT * FROM char_table ORDER BY id ASC")
    fun getAllChars(): LiveData<List<CharEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM char_table WHERE artist_name = :name)")
    fun isCharFavorite(name: String): LiveData<Boolean>

    @Query("SELECT * FROM char_table WHERE artist_name = :name LIMIT 1")
    fun getCharByName(name: String): CharEntity?
}
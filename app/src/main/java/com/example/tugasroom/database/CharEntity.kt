package com.example.tugasroom.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "char_table")
data class CharEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name = "artist_name")
    val artistName: String,

    @ColumnInfo(name = "artist_href")
    val artistHref: String,

    @ColumnInfo(name = "url")
    val url: String
)
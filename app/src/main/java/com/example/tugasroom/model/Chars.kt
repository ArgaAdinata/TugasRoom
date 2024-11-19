package com.example.tugasroom.model

import com.google.gson.annotations.SerializedName

data class Chars(
    @SerializedName("artist_name") val artistName: String,
    @SerializedName("artist_href") val artistHref: String,
    @SerializedName("url") val url: String
)

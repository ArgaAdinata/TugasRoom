package com.example.tugasroom.model

import com.example.tugasroom.model.Chars
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("results") val results: List<Chars>,
)

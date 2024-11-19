package com.example.tugasroom.network

import com.example.tugasroom.model.Result
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("neko?amount=20")
    fun getAllChars(): Call<Result>
}
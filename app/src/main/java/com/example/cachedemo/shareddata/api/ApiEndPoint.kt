package com.example.cachedemo.shareddata.api

import com.example.cachedemo.model.AnimeCharacter
import com.example.cachedemo.model.CharResult
import com.example.cachedemo.model.CharacterRM
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiEndPoint {

    @GET("api/character")
    fun getCharacter(@Query("page") page: String): Call<AnimeCharacter<CharResult>>

    @GET
    fun getCharacterDetail(@Url url: String): Call<CharacterRM>

}
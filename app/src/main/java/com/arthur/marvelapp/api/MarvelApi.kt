package com.arthur.marvelapp.api

import com.arthur.marvelapp.api.entity.HeroeResponse
import com.arthur.marvelapp.api.entity.Response
import com.arthur.marvelapp.model.Character
import com.arthur.marvelapp.model.Data
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MarvelApi {
    @GET("v1/public/characters")
    fun allCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int? = 0
    ): Observable<Response>

    @GET("v1/public/characters/{characterId}")
    fun oneCharacter(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Call<HeroeResponse>

    companion object {
        fun getService(): MarvelApi {

            var httpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com:443/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()

            return retrofit.create<MarvelApi>(
                MarvelApi::class.java
            )
        }
    }

}
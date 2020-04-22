package com.arthur.marvelapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthur.marvelapp.api.MarvelApi
import com.arthur.marvelapp.api.entity.HeroeResponse
import com.arthur.marvelapp.model.Character
import com.arthur.marvelapp.model.Data
import com.arthur.marvelapp.util.ErrorResource
import com.arthur.marvelapp.util.InternetCheck
import com.arthur.marvelapp.util.Resource
import com.arthur.marvelapp.util.SuccessResource
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsHeroesViewModel : ViewModel() {

    lateinit var marvelApi: MarvelApi

    var ts = "1587169291"
    var apikey = "0389342af8aecdff040fc2aca71a2b3a"
    var hash = "db7eff7924ae6fa28bf493dab22be5d2"

    var character = MutableLiveData<Resource<HeroeResponse>>()

    init {
        MarvelApi.getService()
    }

    fun getOneHeroes(idHeroe : Int): MutableLiveData<Resource<HeroeResponse>> {
        val api = MarvelApi.getService()
        api.oneCharacter(idHeroe,ts,apikey,hash).enqueue(object : Callback<HeroeResponse> {
            override fun onFailure(call: Call<HeroeResponse>, t: Throwable) {
                InternetCheck(object : InternetCheck.Consumer {
                    override fun accept(internet: Boolean?) {
                        if (!internet!!) {
                            character.value = ErrorResource("Verifique sua conexão com a internet!")
                        } else {
                            character.value =
                                ErrorResource("Estamos com problemas no servidor, tente novamente mais tarde!")
                        }
                    }
                })
            }

            override fun onResponse(call: Call<HeroeResponse>, response: Response<HeroeResponse>) {
                if (response.isSuccessful) {
                    character.value = SuccessResource(data = response.body()!!)
                } else {
                    val gson = Gson()
                    try {
                        val error: com.arthur.marvelapp.api.entity.Response? = gson.fromJson(
                            response.errorBody()!!.string(),
                            com.arthur.marvelapp.api.entity.Response::class.java
                        )
                        if (error != null) {
                            character.value = ErrorResource(error = error.etag)
                        } else {
                            character.value = ErrorResource("Erro desconhecido!")
                        }
                    } catch (exception: Exception) {
                        character.value = ErrorResource("Problema no servidor!")
                    }
                }
            }
        })
        return character
    }
}
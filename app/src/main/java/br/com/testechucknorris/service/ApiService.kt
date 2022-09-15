package br.com.testechucknorris.service

import br.com.testechucknorris.model.JokeModel
import br.com.testechucknorris.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getRandomJokes(): Call<JokeModel>

    @GET("search")
    fun getJokes(@Query("query") query : String?): Call<SearchModel>

}
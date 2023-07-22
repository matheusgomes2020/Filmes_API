package com.example.filmes.network

import com.example.filmes.model.person.Person
import com.example.filmes.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface PersonApi {

    @GET("person/{personId}?" + Constants.LANGUAGE + "&api_key=" + Constants.API_KEY + "&append_to_response=images,movie_credits,tv_credits")
    suspend fun getPersonInfo(@Path("personId") personId: String ): Person


}
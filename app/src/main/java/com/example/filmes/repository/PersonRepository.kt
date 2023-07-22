package com.example.filmes.repository

import com.example.filmes.data.Resource
import com.example.filmes.model.person.Person
import com.example.filmes.network.PersonApi
import javax.inject.Inject

class PersonRepository @Inject constructor( private val personApi: PersonApi ) {

    suspend fun getPersonInfo(personId: String): Resource<Person> {

        val response = try {
            Resource.Loading( data = true )
            personApi.getPersonInfo( personId )
        }catch ( exception: Exception ) {
            return Resource.Error( message = "An error occurred ${exception.message.toString()}" )
        }
        Resource.Loading( data = false )
        return Resource.Success( data = response )
    }

}
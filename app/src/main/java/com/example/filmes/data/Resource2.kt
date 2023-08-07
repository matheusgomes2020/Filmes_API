package com.example.filmes.data

import java.lang.Exception

sealed class Resource2<out R>{
    data class Success<out R>(val result: R): Resource2<R>()
    class Failure(val exception: Exception): Resource2<Nothing>()
    class Loading: Resource<Nothing>()
}
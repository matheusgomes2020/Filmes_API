package com.example.filmes.ui.personImage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonImageViewModel : ViewModel() {
    private var _personImage = MutableLiveData<String>()
    var carregando: Boolean = true
    val personInfo: LiveData<String> = _personImage


    fun loadImage( image: String ){

        _personImage.value = image

    }

}
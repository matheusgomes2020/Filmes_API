package com.example.filmes.ui.person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.person.Person
import com.example.filmes.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository )
    : ViewModel() {

    private var _personInfo = MutableLiveData<Person>()
    var carregando: Boolean = true
    val personInfo: LiveData<Person> = _personInfo

    fun getPersonInfo( personId: String ) {

        try {
            viewModelScope.launch {
                when (val response = personRepository.getPersonInfo( personId ) ){
                    is Resource.Success -> {
                        _personInfo.value = response.data!!
                        if (_personInfo.value!! != null) carregando = false
                        Log.e("Network", "person: Ok. Certo!!! Carregando?= $carregando")
                    }
                    is Resource.Error -> {
                        carregando = false
                        Log.e("Network", "person: Failed getting person Carregando?= $carregando")
                    }
                    else -> {
                        carregando = false
                    }
                }
            }
        }catch (exception: Exception) {
            carregando = false
            Log.d("Network", "person: ${exception.message.toString()} Carregando?= $carregando")
        }
    }


}
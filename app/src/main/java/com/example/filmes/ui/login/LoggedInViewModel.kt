package com.example.filmes.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

}
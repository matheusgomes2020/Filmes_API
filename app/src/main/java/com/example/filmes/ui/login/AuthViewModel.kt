package com.example.filmes.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieF
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.User
import com.example.filmes.model.movie.Movie
import com.example.filmes.repository.AuthRepository
import com.example.filmes.utils.AuthState
import com.example.filmes.utils.MovieListState
import com.example.filmes.utils.MovieState
import com.example.filmes.utils.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authRepository: AuthRepository,
) : ViewModel() {

    private val _user = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _user

    private val _movie = MutableStateFlow(MovieState())
    private val _movieLL = MutableStateFlow(MovieListState())
   // private val _movies = MutableLiveData(List<MovieRoom>>)
    val movie: StateFlow<MovieState> = _movie
    val movieLL: StateFlow<MovieListState> = _movieLL

    private val _userData = MutableStateFlow(UserState())
    val userData: StateFlow<UserState> = _userData


    private val _movieList = MutableLiveData<List<MovieF>>()
    val movieList: LiveData<List<MovieF>> = _movieList

    fun login(email: String, password: String) {
        authRepository.login(email, password).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _user.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _user.value = AuthState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun register(email: String, password: String, user: User) {
        authRepository.register(email, password, user).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _user.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _user.value = AuthState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun saveMovie(movieRoom: MovieF) {
        authRepository.saveMovie(movieRoom).onEach {
        }.launchIn(viewModelScope)


    }

    fun loggedUser() {

        authRepository.getLoggedUser().onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _user.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _user.value = AuthState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getUserData() {
        authRepository.getUserData().onEach {
            when (it) {
                is Resource.Loading -> {
                    _userData.value = UserState(isLoading = true)
                }
                is Resource.Error -> {
                    _userData.value = UserState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _userData.value = UserState(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getMovies() {
        authRepository.getMovies().onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieLL.value = MovieListState(isLoading = true)
                    Log.d("BBBBGTT", "VM/Loading: " + _movieLL.value)
                }
                is Resource.Error -> {
                    _movieLL.value = MovieListState(error = it.message ?: "")
                    Log.d("BBBBGTT", "VM/Error: " + _movieLL.value)

                }
                is Resource.Success -> {
                    _movieLL.value = MovieListState(data = it.data!!)
                    Log.d("BBBBGTT", "VM/Sucess: " + _movieLL.value)

                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


}
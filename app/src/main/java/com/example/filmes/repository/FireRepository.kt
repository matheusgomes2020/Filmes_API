package com.example.filmes.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieFirebase
import com.example.filmes.model.SeriesFirebase
import com.example.filmes.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FireRepository
    @Inject
    constructor() {

        private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        private var loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()
        private val fireStoreDatabase = FirebaseFirestore.getInstance()


        init {
            if (firebaseAuth.currentUser != null) {
                loggedOutLiveData.postValue(false)
            }
        }

    @SuppressLint("SuspiciousIndentation")
    fun saveMovie(movie: MovieFirebase) : Flow<Resource<MovieFirebase>> = flow {

        emit(Resource.Loading())
        try {
            movie.userId = firebaseAuth.currentUser!!.uid
            val dbCollection = fireStoreDatabase.collection("Movies")
            dbCollection.add(movie).addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                    dbCollection.document(docId)
                        .update(hashMapOf("idFirebase" to docId) as Map<String, Any>)
            }

        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun deleteMovie(movie: MovieFirebase) : Flow<Resource<MovieFirebase>> = flow {

        emit(Resource.Loading())
        try {
            movie.userId = firebaseAuth.currentUser!!.uid
            val dbCollection = fireStoreDatabase.collection("Movies").document(movie.idFirebase)
            dbCollection.delete().await()

        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getMovies(): Flow<Resource<List<MovieFirebase>>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                val snapshot = fireStoreDatabase.collection("Movies")
                    .get().await()
                    .let {
                        it.toObjects(MovieFirebase::class.java)
                    }
                emit(Resource.Success(snapshot))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

    fun saveSeries( series: SeriesFirebase ) : Flow<Resource<SeriesFirebase>> = flow {
        emit(Resource.Loading())
        try {
            series.userId = firebaseAuth.currentUser!!.uid
            val dbCollection = fireStoreDatabase.collection("Series")
            dbCollection.add(series).addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("idFirebase" to docId) as Map<String, Any>)
            }
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun deleteSeries( series: SeriesFirebase ) : Flow<Resource<SeriesFirebase>> = flow {

        emit(Resource.Loading())
        try {
            series.userId = firebaseAuth.currentUser!!.uid
            val dbCollection = fireStoreDatabase.collection("Series").document(series.idFirebase)
            dbCollection.delete().await()

        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }

    fun getSeries(): Flow<Resource<List<SeriesFirebase>>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                val snapshot = fireStoreDatabase.collection("Series")
                    .get().await()
                    .let {
                        it.toObjects(SeriesFirebase::class.java)
                    }
                emit(Resource.Success(snapshot))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }
}
package com.example.filmes.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.filmes.data.Resource
import com.example.filmes.model.MovieF
import com.example.filmes.model.MovieRoom
import com.example.filmes.model.User
import com.example.filmes.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepository
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

    fun saveMovie(movie: MovieF) :Flow<Resource<MovieF>> = flow {

        emit(Resource.Loading())

        try {

            movie.userId = firebaseAuth.currentUser!!.uid
            val dbCollection = fireStoreDatabase.collection("Movies")

            dbCollection.add(movie).await()
                   //val docId = movie.id.toString()
                   // dbCollection.document(docId)
                     //   .update(hashMapOf("id" to docId) as Map<String, Any>).await()

           // dbCollection2.add(movie)
              //  .addOnSuccessListener { documentRef ->
               //     val docId = documentRef.id
                  //  dbCollection.document(docId)
                    //    .update(hashMapOf("id" to docId) as Map<String, Any>)
                      //  .addOnCompleteListener { task ->



                      //  }.addOnFailureListener {
                          //  Log.w("Error", "SaveToFirebase:  Error updating doc",it )
                       // }

              //  }

           // var userMap: MutableMap<String, Any> = HashMap()
            //userMap[movie.id.toString()] = movie
            //val collection = fireStoreDatabase.collection("Movie")
            //collection.document(firebaseAuth.currentUser!!.uid)
              //  .update(userMap).await()


           // fireStoreDatabase.collection("Movie")
               // .document(firebaseAuth.currentUser!!.uid)
               // .set(movie, SetOptions.merge()).await()
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }

    }

    fun register(email: String, password: String, user: User): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())

        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            fireStoreDatabase.collection("User")
                .document(firebaseAuth.currentUser!!.uid)
                .set(user).await()

            fireStoreDatabase.collection("Movies")
                .document(firebaseAuth.currentUser!!.uid)
                .set("movies").await()

            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))
            loggedOutLiveData.postValue(false)
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }


    }



    fun login(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {

        emit(Resource.Loading())

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit((result.user?.let {
                Resource.Success(data = it)
            }!!))
            loggedOutLiveData.postValue(false)

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Your Internet Connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }

    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getLoggedUser(): Flow<Resource<FirebaseUser>> = flow {

        emit(Resource.Loading())

        if (firebaseAuth.currentUser != null) {
            loggedOutLiveData.postValue(false)
            emit(Resource.Success(data = firebaseAuth.currentUser!!))
        } else {
            emit(Resource.Error("Not Logged"))
        }

    }

    fun getUserData(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                val snapshot = fireStoreDatabase.collection("User")
                    .document(firebaseAuth.currentUser!!.uid).get().await()
                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    emit(Resource.Success(data = user!!))
                }
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

    fun getMovies(): Flow<Resource<List<MovieF>>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                    val snapshot = fireStoreDatabase.collection("Movies")
                        .get().await()
                        .let {
                            it.toObjects(MovieF::class.java)

                        }
                emit(Resource.Success(snapshot))
                    Log.d("BBBBGTT", "Snapshot: " + snapshot)


            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
                Log.d("BBBBGTT", "Repo: " + e)
            } catch (e: IOException) {
                Log.d("BBBBGTT", "Repo: " + e)
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                Log.d("BBBBGTT", "Repo: " + e)
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }




}
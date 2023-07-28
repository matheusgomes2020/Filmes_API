package com.example.filmes.di

import android.content.Context
import androidx.room.Room
import com.example.filmes.data.MovieDatabase
import com.example.filmes.data.MovieDao
import com.example.filmes.network.PersonApi
import com.example.filmes.network.MoviesApi
import com.example.filmes.network.SeriesApi
import com.example.filmes.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn( SingletonComponent::class )
object AppModule {

    @Singleton
    @Provides
    fun providesMoviesApi() : MoviesApi {

        return Retrofit.Builder()
            .baseUrl( Constants.BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( MoviesApi::class.java )

    }

    @Singleton
    @Provides
    fun providesSeriesApi() : SeriesApi {

        return Retrofit.Builder()
            .baseUrl( Constants.BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( SeriesApi::class.java )

    }

    @Singleton
    @Provides
    fun providesPersonApi() : PersonApi {

        return Retrofit.Builder()
            .baseUrl( Constants.BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
            .create( PersonApi::class.java )

    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie_db"
    )
        .fallbackToDestructiveMigration()
        .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideMovieDao( movieDatabase: MovieDatabase ) = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun provideSeriesDao( movieDatabase: MovieDatabase ) = movieDatabase.seriesDao()
}
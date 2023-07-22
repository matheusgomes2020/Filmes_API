package com.example.filmes.di

import com.example.filmes.network.PersonApi
import com.example.filmes.network.MoviesApi
import com.example.filmes.network.SeriesApi
import com.example.filmes.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}
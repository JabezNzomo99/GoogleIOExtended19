package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.data.models.Response
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MoviesApiService {

    @GET("trending/movie/week")
    fun getTrendingMoviesThisWeek():Deferred<Response>

    @GET("trending/movie/day")
    fun getTrendingMoviesToday():Deferred<Response>

    companion object {
        operator fun invoke(authenticationInterceptor: AuthenticationInterceptor, connectivityInterceptor: ConnectivityInterceptor):MoviesApiService{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApiService::class.java)
        }
    }
}
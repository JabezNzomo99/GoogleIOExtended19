package com.jabezmagomere.movies.data.network.Api

import com.jabezmagomere.movies.data.models.Response
import com.jabezmagomere.movies.data.network.Interceptors.Connectivity.ConnectivityInterceptor
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.DiscoverAuthenticatorInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DiscoverMoviesApiService {

    @GET("discover/movie?with_genres=28&sort_by=vote_average.desc&vote_count.gte=10")
    suspend fun fetchActionMovies():retrofit2.Response<Response>

    @GET("discover/movie?with_genres=35&sort_by=vote_average.desc&vote_count.gte=10")
    suspend fun fetchComedyMovies():retrofit2.Response<Response>

    companion object {
        operator fun invoke(discoverAuthenticatorInterceptor: DiscoverAuthenticatorInterceptor, connectivityInterceptor: ConnectivityInterceptor): DiscoverMoviesApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(discoverAuthenticatorInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DiscoverMoviesApiService::class.java)
        }
    }
}
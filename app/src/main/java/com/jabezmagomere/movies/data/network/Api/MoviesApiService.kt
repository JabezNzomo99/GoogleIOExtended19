package com.jabezmagomere.movies.data.network.Api

import com.jabezmagomere.movies.data.models.Response
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.AuthenticationInterceptor
import com.jabezmagomere.movies.data.network.Interceptors.Connectivity.ConnectivityInterceptor
import com.jabezmagomere.movies.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MoviesApiService {

    @GET("trending/movie/week")
    suspend fun fetchTrendingMoviesThisWeek():retrofit2.Response<Response>

    @GET("trending/movie/day")
    suspend fun fetchTrendingMoviesToday():retrofit2.Response<Response>

    companion object {
        operator fun invoke(authenticationInterceptor: AuthenticationInterceptor, connectivityInterceptor: ConnectivityInterceptor): MoviesApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApiService::class.java)
        }
    }
}
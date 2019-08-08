package com.jabezmagomere.movies

import android.app.Application
import com.jabezmagomere.movies.data.db.MovieDatabase
import com.jabezmagomere.movies.data.network.*
import com.jabezmagomere.movies.data.network.Api.DiscoverMoviesApiService
import com.jabezmagomere.movies.data.network.Api.MoviesApiService
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.AuthenticationInterceptor
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.AuthenticationInterceptorImpl
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.DiscoverAuthenticatorImpl
import com.jabezmagomere.movies.data.network.Interceptors.Authentication.DiscoverAuthenticatorInterceptor
import com.jabezmagomere.movies.data.network.Interceptors.Connectivity.ConnectivityInterceptor
import com.jabezmagomere.movies.data.network.Interceptors.Connectivity.ConnectivityInterceptorImpl
import com.jabezmagomere.movies.data.repository.MovieRepository
import com.jabezmagomere.movies.data.repository.MovieRepositoryImpl
import com.jabezmagomere.movies.ui.viewmodel.MainActivityViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieApplication:Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MovieApplication))
        bind() from singleton { MovieDatabase(instance()) }
        bind() from singleton { instance<MovieDatabase>().moviesDao() }
        bind<AuthenticationInterceptor>() with singleton { AuthenticationInterceptorImpl() }
        bind<DiscoverAuthenticatorInterceptor>() with singleton { DiscoverAuthenticatorImpl() }
        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }
        bind<DiscoverMoviesApiService>() with singleton {
            DiscoverMoviesApiService(
                instance(),
                instance()
            )
        }
        bind<MoviesApiService>() with singleton {
            MoviesApiService(
                instance(),
                instance()
            )
        }
        bind<AppDataSource>() with singleton { AppDataSourceImpl(instance(),instance()) }
        bind<MovieRepository>() with singleton { MovieRepositoryImpl(instance(),instance())}
        bind() from provider { MainActivityViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()

    }
}
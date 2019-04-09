package com.jabezmagomere.movies

import android.app.Application
import com.jabezmagomere.movies.data.network.*
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
        bind<AuthenticationInterceptor>() with singleton { AuthenticationInterceptorImpl() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton {MoviesApiService(instance(),instance())}
        bind<AppDataSource>() with singleton { AppDataSourceImpl(instance()) }
        bind<MovieRepository>() with singleton { MovieRepositoryImpl(instance()) }
        bind() from provider { MainActivityViewModelFactory(instance()) }
    }

}
package com.jabezmagomere.movies.data.network.Interceptors.Connectivity

import android.content.Context
import android.net.ConnectivityManager
import com.jabezmagomere.movies.util.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context):
    ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
       if(!isOnline()) throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
    private fun isOnline():Boolean{
        val connectionManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
}
package com.jabezmagomere.movies.data.network

import com.jabezmagomere.movies.util.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.file.attribute.AclEntry.newBuilder

class AuthenticationInterceptorImpl : AuthenticationInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .setQueryParameter("api_key",Constants.API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
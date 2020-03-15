package com.example.simplifiedinstagramstorymodule.core.utilities

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DynamicEndPointInterceptor : Interceptor {
    @Volatile
    private var host: HttpUrl? = null

    fun setHost(url: String) {
        this.host = url.toHttpUrlOrNull()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = host?.let {
            if (it.toUrl().toURI().host != null) {
                val newUrl = chain.request().url.newBuilder()
                    .scheme(it.scheme)
                    .host(it.toUrl().toURI().host)
                    .port(it.port)
                    .build()

                return@let chain.request().newBuilder()
                    .url(newUrl)
                    .build()
            } else {
                return@let chain.request()
            }
        }

        return chain.proceed(newRequest!!)
    }
}
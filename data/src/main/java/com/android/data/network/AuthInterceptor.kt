package com.android.data.network

import com.android.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by hassanalizadeh on 28,August,2020
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter("Authorization", "Bearer ${BuildConfig.KEY}")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
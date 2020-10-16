package com.android.data.network

import com.android.data.BuildConfig
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Remote API provider.
 */
@Module
class NetworkModule {


    @Provides
    @Reusable
    fun contributorsDataService(retrofit: Retrofit): DataServiceContributors =
        retrofit.create(DataServiceContributors::class.java)


    @Provides
    @Reusable
    fun apolloClient(okHttpClient: OkHttpClient): ApolloClient = ApolloClient.builder()
        .serverUrl(BuildConfig.BASE_URL_GRAPHQL)
        .okHttpClient(okHttpClient)
        .build()


    @Provides
    @Reusable
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL_REST)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()


    @Provides
    @Reusable
    fun okHttpClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }


    @Provides
    @Reusable
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    companion object {
        const val TIME_OUT: Long = 30
    }

}
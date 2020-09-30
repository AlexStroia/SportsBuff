package com.buffup.app.core.di

import com.buffup.app.BuildConfig
import com.buffup.app.core.api.ApiService
import com.buffup.app.core.api.response.VideoResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal fun createNetworkModule(baseUrl: String) = module {
    single {
        Moshi.Builder()
            .build()
    }

    single(appUsageRetrofit) {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().also {
                it.connectTimeout(60L, TimeUnit.SECONDS)
                it.readTimeout(60L, TimeUnit.SECONDS)
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { get<Retrofit>(appUsageRetrofit).create(ApiService::class.java) }

}

private val appUsageRetrofit get() = StringQualifier("appUsageRetrofit")


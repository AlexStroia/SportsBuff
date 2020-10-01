package com.buffup.app.core.di

import com.buffup.app.core.api.ApiService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal fun createNetworkModule(baseUrl: String) = module {
    single { Moshi.Builder().build() }

    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        builder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().also {
                it.connectTimeout(30, TimeUnit.SECONDS)
                it.readTimeout(30,TimeUnit.SECONDS).build()
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    factory { get<Retrofit>().create(ApiService::class.java) }
}

private val appUsageRetrofit get() = StringQualifier("appUsageRetrofit")


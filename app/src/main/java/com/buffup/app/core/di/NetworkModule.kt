package com.buffup.app.core.di

import com.buffup.app.BuildConfig
import com.buffup.app.core.api.ApiService
import com.buffup.app.core.api.response.VideoResponse
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal fun createNetworkModule(baseUrl: String) = module {
    single {
        Moshi.Builder().build()
    }

    single(appUsageOkHttp) {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        builder.build()
    }

    single(appUsageRetrofit) {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get(appUsageOkHttp))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    factory { get<Retrofit>(appUsageRetrofit).create(ApiService::class.java) }

}

private val appUsageOkHttp get() = StringQualifier("appUsageOkHttp")
private val appUsageRetrofit get() = StringQualifier("appUsageRetrofit")


package com.buffup.app.core.api

import com.buffup.app.core.api.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ApiService {
    @GET("/buffs/{buffId}")
    suspend fun getVideos(@Path("buffId") id: Int): VideoResponse
}
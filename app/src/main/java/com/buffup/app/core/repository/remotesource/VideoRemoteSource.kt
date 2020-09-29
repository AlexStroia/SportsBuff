package com.buffup.app.core.repository.remotesource

import com.buffup.app.core.api.ApiService

internal class VideoRemoteSource internal constructor(private val apiService: ApiService) {

    internal suspend fun getVideos(id: Int) = apiService.getVideos(id)
}
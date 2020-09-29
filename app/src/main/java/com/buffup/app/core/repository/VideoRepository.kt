package com.buffup.app.core.repository

import com.buffup.app.core.api.response.VideoResponse
import com.buffup.app.core.repository.localsource.VideoLocalSource
import com.buffup.app.core.repository.remotesource.VideoRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class VideoRepository
internal constructor(private val videoRemoteSource: VideoRemoteSource, private val localSource: VideoLocalSource) {

    suspend fun getVideos(id: Int) = videoRemoteSource.getVideos(id)

    fun save(videoResponse: VideoResponse) = localSource.save(videoResponse)

}
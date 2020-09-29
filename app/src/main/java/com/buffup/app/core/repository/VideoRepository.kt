package com.buffup.app.core.repository

import com.buffup.app.core.api.response.VideoResponse
import com.buffup.app.core.repository.localsource.VideoLocalSource
import com.buffup.app.core.repository.remotesource.VideoRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class VideoRepository
internal constructor(private val videoRemoteSource: VideoRemoteSource, private val localSource: VideoLocalSource) {

    suspend fun fetchVideos(id: Int) = videoRemoteSource.fetchVideos(id)

    fun getVideo() = localSource.videosChannel.asFlow()

    fun save(videoResponse: VideoResponse) = localSource.save(videoResponse)

}
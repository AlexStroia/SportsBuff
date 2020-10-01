package com.buffup.app.core.repository

import com.buffup.app.core.repository.remotesource.VideoRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class VideoRepository
internal constructor(private val videoRemoteSource: VideoRemoteSource) {

    suspend fun fetchVideos(id: Int) = videoRemoteSource.fetchVideos(id)
}
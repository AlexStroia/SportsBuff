package com.buffup.app.core.repository.localsource

import com.buffup.app.core.api.response.VideoResponse
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.*


@ExperimentalCoroutinesApi
class VideoLocalSource {

    val videosChannel = ConflatedBroadcastChannel<VideoResponse>()

    fun save(videoResponse: VideoResponse) {
        videosChannel.offer(videoResponse)
    }
}
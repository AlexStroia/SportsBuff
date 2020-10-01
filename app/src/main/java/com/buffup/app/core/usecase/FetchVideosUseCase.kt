package com.buffup.app.core.usecase

import com.buffup.app.core.Result
import com.buffup.app.core.api.response.VideoResponse
import com.buffup.app.core.repository.VideoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class FetchVideosUseCase internal constructor(
    private val videoRepository: VideoRepository
) {

    suspend operator fun invoke(id: Int): Result<VideoResponse> = Result {
        val result = videoRepository.fetchVideos(id)
        result
    }
}
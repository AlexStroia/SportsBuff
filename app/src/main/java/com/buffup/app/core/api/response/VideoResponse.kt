package com.buffup.app.core.api.response

import com.buffup.app.core.model.Video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoResponse(
    @Json(name="result")
    val videos: Video
)
package com.buffup.app.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "0")
    val content0: ImageContent,
    @Json(name = "1")
    val content1: ImageContent,
    @Json(name = "2")
    val content2: ImageContent
)
package com.buffup.app.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val id: String,
    val key: String,
    val url: String
)
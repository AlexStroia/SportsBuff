package com.buffup.app.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Answers(
    val id: Int,
    @Json(name="buff_id")
    val buffId: Int,
    val title: String,
    @Json(name = "image")
    val images: List<Image>
)
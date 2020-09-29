package com.buffup.app.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    val id: Int,
    @Json(name = "client_id")
    val clientId: Int,
    @Json(name="stream_id")
    val streamId: Int,
    val priority: Int,
    @Json(name ="created_at")
    val createdAt: String,
    val author: Author,
    val question: Question,
    val answer: List<Answers>,
    val language: String
)
package com.buffup.app.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    @Json(name = "id")
    val id: Int,
    @Json(name = "client_id")
    val clientId: Int,
    @Json(name="stream_id")
    val streamId: Int,
    @Json(name = "priority")
    val priority: Int,
    @Json(name ="created_at")
    val createdAt: String,
    @Json(name = "author")
    val author: Author,
    @Json(name = "question")
    val question: Question,
    @Json(name = "answers")
    val answer: List<Answers>,
    @Json(name = "language")
    val language: String
)
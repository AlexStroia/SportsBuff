package com.buffup.app.core.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val id: Int,
    val title: String,
    val category: Int
)
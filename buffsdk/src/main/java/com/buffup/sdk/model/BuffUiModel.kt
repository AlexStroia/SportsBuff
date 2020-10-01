package com.buffup.sdk.model

sealed class BuffUiModel {
    data class Question(val id: Int, val text: String, val timeInSeconds: Int? = 10) : BuffUiModel()
    data class Answer(val id: Int, val text: String) : BuffUiModel()
    data class Author(val firstName: String, val lastName: String, val image: String) :
        BuffUiModel()
}
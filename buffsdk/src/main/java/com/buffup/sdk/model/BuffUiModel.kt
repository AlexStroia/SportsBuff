package com.buffup.sdk.model

sealed class BuffUiModel {
    data class Question(val text: String)
    data class Answer(val text: String)
    data class Author(val name: String)
}
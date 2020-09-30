package com.buffup.sdk.model

sealed class BuffUiModel {
    data class Question(val id: Int,val text: String): BuffUiModel()
    data class Answer(val id: Int,val text: String): BuffUiModel()
    data class Author(val id: Int,val name: String): BuffUiModel()
}
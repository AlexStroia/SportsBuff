package com.buffup.app.core

sealed class SportsBuffError {
    object NoInternet : SportsBuffError()
    object Unknown : SportsBuffError()
    data class Api(val message: String) : SportsBuffError()
}
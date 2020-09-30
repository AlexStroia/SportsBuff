package com.buffup.app.core

import java.io.IOException

class ApiException(errorMessage: String) : IOException(errorMessage)

sealed class SportsBuffError {
    object NoInternet : SportsBuffError()
    object Unknown : SportsBuffError()
    data class Api(val message: String) : SportsBuffError()
}

fun Throwable.asError(): SportsBuffError = when (this) {
    is ApiException ->SportsBuffError.Api(message.orEmpty())
    is IOException -> SportsBuffError.NoInternet
    else -> SportsBuffError.Unknown
}
package com.malabar.core.failure

sealed class Failure {
    data class ServerError(val throwable: Throwable? = null) : Failure()
    object InternalError : Failure()
}
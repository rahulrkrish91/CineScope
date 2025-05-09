package com.malabar.core.base

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.malabar.core.failure.Failure
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(
    maxRetries: Int = 3,
    apiCall: suspend () -> T
): Either<Failure, T> {
    var currentAttempt = 0
    while (currentAttempt < maxRetries) {
        try {
            return apiCall().right()
        } catch (socketException: SocketTimeoutException) {
            currentAttempt++
            if (currentAttempt == maxRetries) {
                return Failure.ServerError(socketException.cause).left()
            }
        } catch (ioException: IOException) {
            return Failure.ServerError(ioException.cause).left()

        } catch (ex: Exception) {
            return Failure.ServerError(ex.cause).left()
        }
    }
    return Failure.InternalError.left()
}
package com.android.common.error

import com.apollographql.apollo.api.Error
import io.reactivex.exceptions.CompositeException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * [ErrorHandler] convert our server api error response to valid error
 */
object ErrorHandler {

    fun convert(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException -> {
                ErrorThrowable(ErrorCode.ERROR_TIMEOUT, ErrorCode.ERROR_TIMEOUT_MESSAGE)
            }
            is IOException -> {
                ErrorThrowable(ErrorCode.ERROR_IO, ErrorCode.ERROR_IO_MESSAGE)
            }
            else -> {
                ErrorThrowable(ErrorCode.ERROR_HAPPENED, ErrorCode.ERROR_HAPPENED_MESSAGE)
            }
        }
    }

    fun getError(throwable: Throwable): String? {
        var error: String = ErrorCode.ERROR_HAPPENED_MESSAGE

        if (throwable is CompositeException && throwable.exceptions.size > 1) {
            throwable.exceptions.forEach { exception ->
                if (exception is ErrorThrowable && !exception.message.isNullOrEmpty())
                    error = exception.message!!
            }
        } else if (!throwable.message.isNullOrEmpty()) {
            error = when (throwable) {
                is ErrorThrowable -> throwable.message!!
                else -> ErrorCode.ERROR_HAPPENED_MESSAGE
            }
        }
        return error
    }

    fun parseError(errors: List<Error>?): ErrorThrowable {
        return try {
            val result = errors?.joinToString { it.message }
            ErrorThrowable(
                ErrorCode.ERROR_HAPPENED,
                result
            )
        } catch (e: Exception) {
            ErrorThrowable(ErrorCode.ERROR_HAPPENED, ErrorCode.ERROR_HAPPENED_MESSAGE)
        }
    }
}
package com.android.common.error

import com.google.gson.Gson
import io.reactivex.exceptions.CompositeException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * [ErrorHandler] convert our server api error response to valid error
 */
object ErrorHandler {

    fun convert(throwable: Throwable): Throwable {
        return when (throwable) {
//            is HttpException -> {
//                parseError(ErrorCode.ERROR_HAPPENED_MESSAGE, throwable.code())
//            }
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

    private fun parseError(errorBody: String?, httpCode: Int = 0): ErrorThrowable {
        return try {
            val result = Gson().fromJson(errorBody, ErrorModel::class.java)
            ErrorThrowable(
                httpCode,
                result.error.errorMessage
            )
        } catch (e: Exception) {
            ErrorThrowable(ErrorCode.ERROR_HAPPENED, ErrorCode.ERROR_HAPPENED_MESSAGE)
        }
    }

    fun isNetworkError(throwable: Throwable): Int? {
        if (throwable is CompositeException)
            throwable.exceptions.forEach {
                if (
                    it is ErrorThrowable &&
                    (it.code == ErrorCode.ERROR_IO || it.code == ErrorCode.ERROR_TIMEOUT)
                ) return it.code
            }
        if (throwable is ErrorThrowable && (throwable.code == ErrorCode.ERROR_IO || throwable.code == ErrorCode.ERROR_TIMEOUT))
            return throwable.code

        return null
    }

}
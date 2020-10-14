package com.android.common.error

/**
 * Created by hassanalizadeh on 19,September,2020
 */
object ErrorCode {
    const val ERROR_HAPPENED = 1000
    const val ERROR_TIMEOUT = 1001
    const val ERROR_IO = 1002
    const val ERROR_EMPTY_STATE = 1003

    const val ERROR_HAPPENED_MESSAGE =
        "Unfortunately, an error occurred while executing your request"
    const val ERROR_TIMEOUT_MESSAGE = "Please try again"
    const val ERROR_IO_MESSAGE = "Problems in connection"
}
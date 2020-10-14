package com.android.common.error

/**
 * Created by hassanalizadeh on 19,September,2020
 */
class ErrorThrowable(
    val code: Int,
    message: String?
) : Throwable(message) {

    constructor(code: Int) : this(code, null)
}
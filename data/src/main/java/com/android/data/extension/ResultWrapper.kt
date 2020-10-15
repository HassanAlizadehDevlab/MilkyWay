package com.android.data.extension

import com.android.common.error.ErrorHandler
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Extension to get and handle errors
 */
fun <T> Flowable<T>.onError(): Flowable<T> =
    this.doOnError {
        throw it.asErrorResult()
    }

fun <T> Observable<T>.onError(): Observable<T> =
    this.doOnError {
        throw it.asErrorResult()
    }

fun <T> Single<T>.onError(): Single<T> =
    this.doOnError {
        throw it.asErrorResult()
    }

fun Completable.onError(): Completable =
    this.doOnError {
        throw it.asErrorResult()
    }


fun Throwable.asErrorResult(): Throwable = ErrorHandler.convert(this)
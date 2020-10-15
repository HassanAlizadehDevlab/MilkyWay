package com.android.domain.usecase

import io.reactivex.Observable

/**
 * Created by hassanalizadeh on 25,February,2019
 */
abstract class UseCaseObservable<R, P> {

    operator fun invoke(param: P?): Observable<R> {
        return if (param != null) {
            execute(param)
        } else {
            Observable.error(IllegalArgumentException())
        }
    }

    protected abstract fun execute(param: P): Observable<R>

}

operator fun <R> UseCaseObservable<R, Unit>.invoke(): Observable<R> = this(Unit)
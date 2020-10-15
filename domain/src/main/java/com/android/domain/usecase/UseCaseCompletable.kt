package com.android.domain.usecase

import io.reactivex.Completable

/**
 * Created by hassanalizadeh on 25,February,2019
 */
abstract class UseCaseCompletable<P> {

    operator fun invoke(param: P?): Completable {
        return if (param != null) {
            execute(param)
        } else {
            Completable.error(IllegalArgumentException())
        }
    }

    protected abstract fun execute(param: P): Completable

}

operator fun UseCaseCompletable<Unit>.invoke(): Completable = this(Unit)
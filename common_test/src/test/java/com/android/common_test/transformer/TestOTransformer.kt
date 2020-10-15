package com.android.common_test.transformer

import com.android.domain.executor.transformer.OTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource

/**
 * Created by hassanalizadeh on 21,March,2019
 */
class TestOTransformer<T> : OTransformer<T>() {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
    }

}
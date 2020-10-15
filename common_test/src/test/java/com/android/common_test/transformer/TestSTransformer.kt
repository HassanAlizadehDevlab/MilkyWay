package com.android.common_test.transformer

import com.android.domain.executor.transformer.STransformer
import io.reactivex.Single
import io.reactivex.SingleSource

/**
 * Created by hassanalizadeh on 21,March,2019
 */
class TestSTransformer<T> : STransformer<T>() {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream
    }

}
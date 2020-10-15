package com.android.common_test.transformer

import com.android.domain.executor.transformer.CTransformer
import io.reactivex.Completable
import io.reactivex.CompletableSource

/**
 * Created by hassanalizadeh on 21,March,2019
 */
class TestCTransformer : CTransformer() {

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
    }

}
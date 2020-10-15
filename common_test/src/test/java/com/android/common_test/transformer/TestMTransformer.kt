package com.android.common_test.transformer

import com.android.domain.executor.transformer.MTransformer
import io.reactivex.Maybe
import io.reactivex.MaybeSource

/**
 * Created by Mousa on 11/24/19.
 */
class TestMTransformer<T> : MTransformer<T>() {

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream
    }

}
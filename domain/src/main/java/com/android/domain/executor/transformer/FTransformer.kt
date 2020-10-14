package com.android.domain.executor.transformer

import io.reactivex.FlowableTransformer

/**
 * A transformer to io thread for Flowable.
 */
abstract class FTransformer<T> : FlowableTransformer<T, T>
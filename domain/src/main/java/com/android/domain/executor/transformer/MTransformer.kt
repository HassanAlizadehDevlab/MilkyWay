package com.android.domain.executor.transformer

import io.reactivex.MaybeTransformer

/**
 * A transformer to io thread for Maybe.
 */
abstract class MTransformer<T> : MaybeTransformer<T, T>
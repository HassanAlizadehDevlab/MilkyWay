package com.android.domain

import com.android.domain.entity.RepositoriesObject
import com.android.domain.executor.transformer.AsyncCTransformer
import com.android.domain.executor.transformer.AsyncFTransformer
import com.android.domain.executor.transformer.CTransformer
import com.android.domain.executor.transformer.FTransformer
import dagger.Binds
import dagger.Module

/**
 * Created by hassanalizadeh on 19,September,2020
 */
@Module
abstract class DomainModule {

    @Binds
    abstract fun completableTransformer(
        transformer: AsyncCTransformer
    ): CTransformer

    @Binds
    abstract fun githubRepositoriesTransformer(
        transformer: AsyncFTransformer<RepositoriesObject>
    ): FTransformer<RepositoriesObject>

}
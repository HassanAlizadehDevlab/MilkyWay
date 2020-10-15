package com.android.domain.usecase.repositories

import com.android.domain.executor.transformer.CTransformer
import com.android.domain.repository.GithubRepositoriesRepository
import com.android.domain.usecase.UseCaseCompletable
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 15,October,2020
 */
class FetchMoreRepositoriesUseCase @Inject constructor(
    private val repository: GithubRepositoriesRepository,
    private val transformer: CTransformer
) : UseCaseCompletable<Unit>() {
    override fun execute(param: Unit): Completable {
        return repository.fetchMoreRepositories()
            .compose(transformer)
    }
}
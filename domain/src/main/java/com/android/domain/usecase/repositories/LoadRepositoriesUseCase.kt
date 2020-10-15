package com.android.domain.usecase.repositories

import com.android.domain.entity.RepositoriesObject
import com.android.domain.executor.transformer.CTransformer
import com.android.domain.executor.transformer.FTransformer
import com.android.domain.repository.GithubRepositoriesRepository
import com.android.domain.usecase.UseCaseCompletable
import com.android.domain.usecase.UseCaseFlowable
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 15,October,2020
 */
class LoadRepositoriesUseCase @Inject constructor(
    private val repository: GithubRepositoriesRepository,
    private val transformer: FTransformer<RepositoriesObject>
) : UseCaseFlowable<RepositoriesObject, Unit>() {
    override fun execute(param: Unit): Flowable<RepositoriesObject> {
        return repository.loadRepositories()
            .compose(transformer)
    }
}
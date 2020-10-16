package com.android.domain.usecase.detail

import com.android.domain.entity.DetailObject
import com.android.domain.executor.transformer.STransformer
import com.android.domain.repository.DetailRepository
import com.android.domain.repository.GithubRepositoriesRepository
import com.android.domain.usecase.UseCaseSingle
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class FetchDetailUseCase @Inject constructor(
    private val githubRepository: GithubRepositoriesRepository,
    private val detailRepository: DetailRepository,
    private val transformer: STransformer<DetailObject>
) : UseCaseSingle<DetailObject, String>() {
    /**
     * @param param is ownerWithRepo
     * */
    override fun execute(param: String): Single<DetailObject> {
        if (param.isEmpty()) return Single.error(IllegalArgumentException())
        if (param.split("/").size != 2) return Single.error(IllegalArgumentException())

        param.split("/").let {
            return githubRepository.getRepositoryByName(param)
                .zipWith(
                    detailRepository.fetchContributors(it[0], it[1])
                        .onErrorReturn { listOf() }
                ) { first, second ->
                    DetailObject(first, second)
                }.compose(transformer)
        }
    }
}
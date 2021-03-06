package com.android.data.repository.datasource.repositories

import com.android.data.entity.model.local.RepositoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by hassanalizadeh on 14,October,2020
 */
interface RepositoriesDataSource {
    fun fetchRepositories(): Completable
    fun fetchMoreRepositories(): Completable

    /**
     * first is the list of repositories
     * second is hasNextPage
     * */
    fun loadRepositories(): Flowable<Pair<List<RepositoryEntity>, Boolean>>
    fun getRepository(name: String): Single<RepositoryEntity>
}
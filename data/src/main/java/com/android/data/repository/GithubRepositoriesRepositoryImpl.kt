package com.android.data.repository

import com.android.data.entity.mapper.map
import com.android.data.repository.datasource.repositories.RepositoriesDataSource
import com.android.domain.entity.RepositoriesObject
import com.android.domain.entity.RepositoryObject
import com.android.domain.repository.GithubRepositoriesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 15,October,2020
 */
class GithubRepositoriesRepositoryImpl @Inject constructor(
    private val dataSource: RepositoriesDataSource
) : GithubRepositoriesRepository {

    override fun fetchRepositories(): Completable {
        return dataSource.fetchRepositories()
    }

    override fun fetchMoreRepositories(): Completable {
        return dataSource.fetchMoreRepositories()
    }

    override fun loadRepositories(): Flowable<RepositoriesObject> {
        return dataSource.loadRepositories().map { it.map() }
    }

    override fun getRepositoryByName(name: String): Single<RepositoryObject> {
        return dataSource.getRepository(name).map { it.map() }
    }

}
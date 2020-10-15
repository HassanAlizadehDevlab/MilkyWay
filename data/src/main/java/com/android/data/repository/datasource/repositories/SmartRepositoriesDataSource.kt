package com.android.data.repository.datasource.repositories

import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.dao.RepositoriesDao
import com.android.data.entity.mapper.map
import com.android.data.entity.model.local.RepositoryEntity
import com.android.data.extension.onError
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 15,October,2020
 */
open class SmartRepositoriesDataSource @Inject constructor(
    private val service: ApolloClient,
    private val repositoryDao: RepositoriesDao
) : RepositoriesDataSource {

    @Volatile
    private var hasNextPage = true

    override fun loadRepositories(): Flowable<Pair<List<RepositoryEntity>, Boolean>> {
        return repositoryDao.selectAll()
            .map {
                it to hasNextPage
            }.onError()
    }

    override fun fetchRepositories(): Completable {
        return getQueryParams()
            .flatMap { Single.fromObservable(service.rxQuery(it)) }
            .flatMap { clearLaunches().toSingle { it } }
            .flatMapCompletable { response ->
                insertLaunches(response.data?.search()?.nodes()?.map {
                    it as LoadRepositoriesQuery.AsRepository
                    it.map()
                })
            }
            .onError()
    }

    private fun insertLaunches(repositories: List<RepositoryEntity>?): Completable {
        if (repositories.isNullOrEmpty()) return Completable.complete()
        return Completable.fromAction { repositoryDao.insert(repositories) }
            .onError()
    }

    private fun clearLaunches(): Completable {
        return Completable.fromAction { repositoryDao.deleteAll() }
    }

    private fun getQueryParams(): Single<LoadRepositoriesQuery> {
        return Single.fromCallable {
            return@fromCallable LoadRepositoriesQuery
                .builder()
                .build()
        }
    }

}
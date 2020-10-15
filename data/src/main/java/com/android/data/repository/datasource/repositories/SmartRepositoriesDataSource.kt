package com.android.data.repository.datasource.repositories

import com.android.common.error.ErrorHandler
import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.dao.RepositoriesDao
import com.android.data.entity.mapper.map
import com.android.data.entity.model.local.RepositoryEntity
import com.android.data.extension.onError
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
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

    @Volatile
    private var endCursor: String? = null

    override fun loadRepositories(): Flowable<Pair<List<RepositoryEntity>, Boolean>> {
        return repositoryDao.selectAll()
            .map {
                it to hasNextPage
            }.onError()
    }

    override fun fetchRepositories(): Completable {
        return resetParams()
            .andThen(getQueryParams())
            .flatMap { getGithubRepositories(it) }
            .flatMap { checkError(it) }
            .flatMap { setCursor(it).toSingle { it } }
            .flatMap { clearRepositories().toSingle { it } }
            .flatMapCompletable { insertRepositories(it) }
            .onError()
    }

    override fun fetchMoreRepositories(): Completable {
        return getQueryParams()
            .flatMap { getGithubRepositories(it) }
            .flatMap { checkError(it) }
            .flatMap { setCursor(it).toSingle { it } }
            .flatMapCompletable { insertRepositories(it) }
            .onError()
    }

    private fun getGithubRepositories(params: LoadRepositoriesQuery): Single<Response<LoadRepositoriesQuery.Data>> {
        return Single.fromObservable(service.rxQuery(params))
    }

    private fun insertRepositories(response: Response<LoadRepositoriesQuery.Data>): Completable {
        val repositories = response.data?.search()?.nodes()?.map {
            it as LoadRepositoriesQuery.AsRepository
            it.map()
        }
        if (repositories.isNullOrEmpty()) return Completable.complete()
        return Completable.fromAction { repositoryDao.insert(repositories) }
            .onError()
    }

    private fun clearRepositories(): Completable {
        return Completable.fromAction { repositoryDao.deleteAll() }
    }

    private fun checkError(response: Response<LoadRepositoriesQuery.Data>): Single<Response<LoadRepositoriesQuery.Data>> {
        return if (response.hasErrors()) throw ErrorHandler.parseError(response.errors)
        else Single.fromCallable { response }
    }

    private fun resetParams(): Completable {
        return Completable.fromAction {
            hasNextPage = true
            endCursor = null
        }
    }

    private fun getQueryParams(): Single<LoadRepositoriesQuery> {
        return Single.fromCallable {
            return@fromCallable LoadRepositoriesQuery
                .builder()
                .after(endCursor)
                .build()
        }
    }

    private fun setCursor(response: Response<LoadRepositoriesQuery.Data>): Completable {
        return Completable.fromCallable {
            response.data?.search()?.pageInfo()?.endCursor()?.let {
                this.endCursor = it
            }
        }
    }

}
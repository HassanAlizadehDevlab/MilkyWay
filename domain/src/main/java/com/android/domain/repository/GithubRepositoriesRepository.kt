package com.android.domain.repository

import com.android.domain.entity.RepositoriesObject
import com.android.domain.entity.RepositoryObject
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by hassanalizadeh on 15,October,2020
 */
interface GithubRepositoriesRepository {
    fun fetchRepositories(): Completable
    fun fetchMoreRepositories(): Completable
    fun loadRepositories(): Flowable<RepositoriesObject>
    fun getRepositoryByName(name: String): Single<RepositoryObject>
}
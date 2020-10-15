package com.android.domain.repository

import com.android.domain.entity.RepositoriesObject
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by hassanalizadeh on 15,October,2020
 */
interface RepositoriesRepository {
    fun fetchRepositories(): Completable
    fun fetchMoreRepositories(): Completable
    fun loadRepositories(): Flowable<RepositoriesObject>
}
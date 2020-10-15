package com.android.data.repository

import com.android.data.entity.mapper.map
import com.android.data.repository.datasource.detail.DetailDataSource
import com.android.domain.entity.ContributorObject
import com.android.domain.repository.DetailRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class DetailRepositoryImpl @Inject constructor(
    private val dataSource: DetailDataSource
) : DetailRepository {
    override fun fetchContributors(owner: String, repo: String): Single<List<ContributorObject>> {
        return dataSource.fetchContributors(owner, repo).map { it.map() }
    }
}
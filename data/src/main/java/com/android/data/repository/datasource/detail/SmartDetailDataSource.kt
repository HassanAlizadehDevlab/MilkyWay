package com.android.data.repository.datasource.detail

import com.android.data.entity.model.remote.Contributor
import com.android.data.network.DataServiceContributors
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
open class SmartDetailDataSource @Inject constructor(
    private val service: DataServiceContributors
) : DetailDataSource {
    override fun fetchContributors(owner: String, repo: String): Single<List<Contributor>> {
        return service.getContributors(owner, repo)
    }
}
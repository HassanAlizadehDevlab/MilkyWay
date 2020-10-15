package com.android.data.repository.datasource.detail

import com.android.data.entity.model.remote.Contributor
import io.reactivex.Single

/**
 * Created by hassanalizadeh on 16,October,2020
 */
interface DetailDataSource {
    fun fetchContributors(owner: String, repo: String): Single<List<Contributor>>
}
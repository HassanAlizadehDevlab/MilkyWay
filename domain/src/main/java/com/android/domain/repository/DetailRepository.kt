package com.android.domain.repository

import com.android.domain.entity.ContributorObject
import io.reactivex.Single

/**
 * Created by hassanalizadeh on 15,October,2020
 */
interface DetailRepository {
    fun fetchContributors(owner: String, repo: String): Single<List<ContributorObject>>
}
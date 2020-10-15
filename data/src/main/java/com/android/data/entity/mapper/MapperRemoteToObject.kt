package com.android.data.entity.mapper

import com.android.data.entity.model.remote.Contributor
import com.android.domain.entity.ContributorObject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
fun List<Contributor>.map() = map { it.map() }
fun Contributor.map() = ContributorObject(
    id = id,
    login = login,
    avatar_url = avatar_url
)
package com.android.data.entity.mapper

import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.model.local.RepositoryEntity

/**
 * Created by hassanalizadeh on 15,October,2020
 */
fun LoadRepositoriesQuery.AsRepository.map() = RepositoryEntity(
    nameWithOwner = nameWithOwner(),
    createdAt = createdAt().toString(),
    stargazerCount = stargazerCount(),
    forkCount = forkCount(),
    description = description().toString(),
    url = url().toString()
)
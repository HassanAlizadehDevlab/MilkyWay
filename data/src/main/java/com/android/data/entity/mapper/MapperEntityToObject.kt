package com.android.data.entity.mapper

import com.android.data.entity.model.local.RepositoryEntity
import com.android.domain.entity.RepositoriesObject
import com.android.domain.entity.RepositoryObject

/**
 * Created by hassanalizadeh on 15,October,2020
 */
fun Pair<List<RepositoryEntity>, Boolean>.map() =
    RepositoriesObject(
        repositories = first.map(),
        hasNextPage = second
    )

fun List<RepositoryEntity>.map() = map { it.map() }

fun RepositoryEntity.map() = RepositoryObject(
    nameWithOwner = nameWithOwner,
    createdAt = createdAt,
    stargazerCount = stargazerCount,
    forkCount = forkCount,
    description = description,
    url = url
)
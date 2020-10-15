package com.android.domain.entity

/**
 * Created by hassanalizadeh on 15,October,2020
 */
data class RepositoriesObject(
    val repositories: List<RepositoryObject>,
    val hasNextPage: Boolean
): DomainObject
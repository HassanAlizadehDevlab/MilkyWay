package com.android.domain.entity

/**
 * Created by hassanalizadeh on 16,October,2020
 */
data class DetailObject(
    val repository: RepositoryObject,
    val contributors: List<ContributorObject>
)
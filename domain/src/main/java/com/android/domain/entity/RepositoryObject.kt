package com.android.domain.entity

/**
 * Created by hassanalizadeh on 15,October,2020
 */
data class RepositoryObject(
    val nameWithOwner: String,
    val createdAt: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val description: String,
    val url: String
) : DomainObject
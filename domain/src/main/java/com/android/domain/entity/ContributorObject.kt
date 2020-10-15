package com.android.domain.entity

/**
 * Created by hassanalizadeh on 16,October,2020
 */
data class ContributorObject(
    val id: Int,
    val login: String,
    val avatar_url: String,
): DomainObject
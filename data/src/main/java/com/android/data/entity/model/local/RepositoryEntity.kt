package com.android.data.entity.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey
    val nameWithOwner: String,
    val createdAt: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val description: String,
    val url: String
)
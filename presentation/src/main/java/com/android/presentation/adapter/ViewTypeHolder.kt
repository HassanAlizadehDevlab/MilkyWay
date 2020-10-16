package com.android.presentation.adapter

import com.android.domain.entity.DomainObject
import com.android.domain.entity.LoadMoreObject
import com.android.domain.entity.RepositoryObject
import com.android.presentation.R

/**
 * Created by hassanalizadeh on 26,February,2019
 */
object ViewTypeHolder {

    val LOAD_MORE_VIEW: Int = R.layout.adapter_load_more
    val REPOSITORY_VIEW: Int = R.layout.adapter_repository

    fun getView(obj: DomainObject?): Int {
        if (obj == null) return 0
        return when (obj::class) {
            LoadMoreObject::class -> LOAD_MORE_VIEW
            RepositoryObject::class -> REPOSITORY_VIEW

            else -> 0
        }
    }
}

package com.android.presentation.adapter

import com.android.domain.entity.DomainObject

/**
 * Created by hassanalizadeh on 26,February,2019
 */
object ViewTypeHolder {

    fun getView(obj: DomainObject?): Int {
        if (obj == null) return 0
        return when (obj::class) {
            else -> 0
        }
    }
}

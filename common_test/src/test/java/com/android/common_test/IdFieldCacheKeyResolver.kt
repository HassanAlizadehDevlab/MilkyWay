package com.android.common_test

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver

/**
 * Created by hassanalizadeh on 15,October,2020
 */
class IdFieldCacheKeyResolver : CacheKeyResolver() {

    private fun formatCacheKey(id: String?): CacheKey {
        return if (id == null || id.isEmpty()) {
            CacheKey.NO_KEY
        } else {
            CacheKey(id)
        }
    }

    override fun fromFieldArguments(
        field: ResponseField,
        variables: Operation.Variables
    ): CacheKey {
        val id = field.resolveArgument("id", variables)
        return if (id != null) {
            formatCacheKey(id.toString())
        } else {
            formatCacheKey(null)
        }
    }

    override fun fromFieldRecordSet(
        field: ResponseField,
        recordSet: kotlin.collections.Map<String, Any>
    ): CacheKey {
        val id = recordSet["id"]
        return if (id != null) {
            formatCacheKey(id.toString())
        } else {
            formatCacheKey(null)
        }
    }
}
package com.android.presentation.ui.repositories.adapter

import android.view.ViewGroup
import com.android.domain.entity.DomainObject
import com.android.presentation.adapter.*
import com.android.presentation.common.extension.inflate

/**
 * Created by hassanalizadeh on 19,September,2020
 */
class RepositoryAdapter(
    private val listener: (BaseViewHolder<out DomainObject>) -> Unit
) : BaseRecyclerAdapter(CONFIG) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out DomainObject> {
        val view = parent.inflate(viewType)
        val holder = when (viewType) {
            ViewTypeHolder.LOAD_MORE_VIEW -> LoadMoreViewHolder(view)
            ViewTypeHolder.REPOSITORY_VIEW -> RepositoryViewHolder(view)
            else -> EmptyViewHolder(view)
        }
        listener(holder)
        return holder
    }

    companion object {
        val CONFIG = Config.Builder()
            .setPreFetch(10)
            .setScreenSize(7)
            .build()
    }

}
package com.android.presentation.adapter

import android.view.View
import com.android.domain.entity.DomainObject
import com.android.domain.entity.LoadMoreObject

/**
 * Created by hassanalizadeh on 04,March,2019
 */
class LoadMoreViewHolder(val view: View) : BaseViewHolder<LoadMoreObject>(view) {

    override fun getType(): Int = ViewTypeHolder.LOAD_MORE_VIEW

    override fun bind(data: DomainObject?) {

    }

}
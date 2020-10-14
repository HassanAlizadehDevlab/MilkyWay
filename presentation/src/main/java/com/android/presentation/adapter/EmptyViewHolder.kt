package com.android.presentation.adapter

import android.view.View
import com.android.domain.entity.DomainObject

/**
 * Created by hassanalizadeh on 19,February,2019
 */
class EmptyViewHolder(val view: View) : BaseViewHolder<DomainObject>(view) {

    override fun getType(): Int = 0

    override fun bind(data: DomainObject?) {}

}
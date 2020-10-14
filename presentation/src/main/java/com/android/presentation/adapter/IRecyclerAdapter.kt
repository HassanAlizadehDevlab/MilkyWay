package com.android.presentation.adapter

import com.android.domain.entity.DomainObject

/**
 * Created by hassanalizadeh on 07,March,2019
 */
interface IRecyclerAdapter {

    fun <T : DomainObject> addItem(index: Int, item: T)

    fun <T : DomainObject> addItem(item: T)

    fun <T : DomainObject> addItems(items: MutableList<T>)

    fun addItemsList(items: List<*>)

    fun <T : DomainObject> addItemsWithoutClear(items: MutableList<T>)

    fun remove(index: Int)

    fun removeAll()

}
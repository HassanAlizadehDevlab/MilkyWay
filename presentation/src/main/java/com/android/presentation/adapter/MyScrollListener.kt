package com.android.presentation.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hassanalizadeh on 09,September,2020
 */
class MyScrollListener(private val onScrollChanged: () -> Unit) : RecyclerView.OnScrollListener() {

    private var lastVisibleItemPosition = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lm = recyclerView.layoutManager as LinearLayoutManager
        val new = lm.findFirstVisibleItemPosition()

        if (lastVisibleItemPosition < new) {
            onScrollChanged.invoke()
        }

        lastVisibleItemPosition = new
    }

}
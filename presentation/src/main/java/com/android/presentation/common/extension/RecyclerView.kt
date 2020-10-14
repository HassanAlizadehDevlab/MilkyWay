package com.android.presentation.common.extension

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.presentation.common.utils.GridSpacingDecoration

/**
 * Created by hassanalizadeh on 06,March,2019
 */
fun RecyclerView.linearLayout(
    context: Context,
    spacing: Int = 0,
    noRightSpacingForFirstItem: Boolean = false,
    noTopSpacingForFirstItem: Boolean = false,
    spanCount: Int? = 1,
    @RecyclerView.Orientation orientation: Int? = RecyclerView.VERTICAL,
    reverseLayout: Boolean? = false,
    stackFromEnd: Boolean? = false
) {
    val lm = LinearLayoutManager(context, orientation!!, reverseLayout!!)
    lm.stackFromEnd = stackFromEnd!!
    layoutManager = lm
    setHasFixedSize(true)
    addItemDecoration(
        GridSpacingDecoration(
            spanCount = spanCount!!,
            spacing = spacing,
            noRightSpacingForFirstItem = noRightSpacingForFirstItem,
            noTopSpacingForFirstItem = noTopSpacingForFirstItem,
            includeEdge = true
        )
    )
}
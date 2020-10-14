package com.android.presentation.common.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hassanalizadeh on 13,September,2018
 */
class GridSpacingDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val noRightSpacingForFirstItem: Boolean = false,
    private val noTopSpacingForFirstItem: Boolean = false,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            // outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            // outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (spanCount > 1 && position == (spanCount - 1)) {
                outRect.left = spacing
            } else {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            }

            // Spacing for first item when spanCount is bigger that 1{
            if (spanCount > 1 && position == 0 && !noRightSpacingForFirstItem) {
                outRect.right = spacing
            } else {
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            }

            if (position < spanCount) { // top edge
                if (!noTopSpacingForFirstItem)
                    outRect.top = spacing
            }

            if (noTopSpacingForFirstItem)
                outRect.top = spacing // item top
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}
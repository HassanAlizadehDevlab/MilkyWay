package com.android.presentation.common.utils

import android.content.Context
import android.content.res.Resources

/**
 * Created by hassanalizadeh on 09,March,2019
 */
object Utils {

    fun getDeviceWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun convertPxToDp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

}
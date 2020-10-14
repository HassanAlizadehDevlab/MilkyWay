package com.android.common.extension

/**
 * Created by hassanalizadeh on 19,September,2020
 */
fun Int?.or(value: Int) = this ?: value
fun Int?.orZero(): Int = this ?: 0
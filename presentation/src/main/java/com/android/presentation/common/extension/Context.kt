package com.android.presentation.common.extension

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Created by hassanalizadeh on 28,August,2020
 */
@SuppressLint("InflateParams")
fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

@SuppressLint("InflateParams")
fun Context.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) {
    val text = this.getString(textId)
    Toast.makeText(this, text, duration).show()
}
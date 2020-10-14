package com.android.common.error

import com.google.gson.annotations.SerializedName

/**
 * Created by hassanalizadeh on 19,September,2020
 */
data class ErrorModel(@SerializedName("meta") val error: Error)
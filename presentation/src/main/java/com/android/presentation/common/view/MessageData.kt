package com.android.presentation.common.view

import androidx.annotation.StringRes

/**
 * Created by hassanalizadeh on 28,August,2020
 */
class MessageData(
    var message: String? = null,
    @StringRes
    var resource: Int? = null
)
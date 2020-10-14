package com.android.presentation.common.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

/**
 * Created by hassanalizadeh on 30,August,2020
 */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> = Transformations.map(this, body)

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this, body)

fun <X> MutableLiveData<X>.notifyObservers() {
    this.value = this.value     //In order to trigger value change
}

fun <X> MutableLiveData<X>.clear() {
    this.value = null
}
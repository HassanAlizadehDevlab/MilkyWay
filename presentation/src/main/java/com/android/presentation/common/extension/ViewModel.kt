package com.android.presentation.common.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.android.presentation.common.viewmodel.ViewModelProviderFactory

/**
 * Created by hassanalizadeh on 28,August,2020
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this, Observer { t -> action(t) })
}

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(provider: ViewModelProviderFactory) =
    ViewModelProvider(this, provider)[VM::class.java]


inline fun <reified VM : ViewModel> AppCompatActivity.viewModelProvider(provider: ViewModelProviderFactory) =
    ViewModelProvider(this, provider)[VM::class.java]


inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(provider: ViewModelProviderFactory) =
    ViewModelProvider(requireActivity(), provider)[VM::class.java]
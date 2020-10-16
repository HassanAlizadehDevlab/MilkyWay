package com.android.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.android.presentation.common.di.FragmentScope
import com.android.presentation.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@Module
abstract class DetailViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(DetailViewModel::class)
    abstract fun repositoryViewModel(viewModel: DetailViewModel): ViewModel
}
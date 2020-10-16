package com.android.presentation.ui.repositories

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
abstract class RepositoryViewModelModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(RepositoryViewModel::class)
    abstract fun repositoryViewModel(viewModel: RepositoryViewModel): ViewModel
}
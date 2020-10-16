package com.android.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import com.android.presentation.common.di.ActivityScope
import com.android.presentation.common.di.FragmentScope
import com.android.presentation.common.view.BaseActivityModule
import com.android.presentation.ui.detail.DetailFragment
import com.android.presentation.ui.detail.DetailFragmentModule
import com.android.presentation.ui.repositories.RepositoryFragment
import com.android.presentation.ui.repositories.RepositoryFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@Module(
    includes = [
        BaseActivityModule::class
    ]
)
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepositoryFragmentModule::class])
    abstract fun repositoriesFragmentInjector(): RepositoryFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    abstract fun detailFragmentInjector(): DetailFragment

    @Binds
    @ActivityScope
    abstract fun activity(mainActivity: MainActivity): AppCompatActivity

}
package com.android.presentation.common.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.android.presentation.common.di.ActivityScope
import com.android.presentation.common.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class BaseActivityModule {

    @Binds
    @ActivityScope
    abstract fun activityContext(appCompatActivity: AppCompatActivity): Context

    /**
     * Bind it just once
     * This is used with Dagger MultiBinding
     * */
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory


    @Module
    companion object {

        const val ACTIVITY_FRAGMENT_MANAGER = "MainActivityFragmentManager"

        @JvmStatic
        @Provides
        @Named(ACTIVITY_FRAGMENT_MANAGER)
        @ActivityScope
        fun provideActivityFragmentManager(appCompatActivity: AppCompatActivity): FragmentManager {
            return appCompatActivity.supportFragmentManager
        }
    }

}
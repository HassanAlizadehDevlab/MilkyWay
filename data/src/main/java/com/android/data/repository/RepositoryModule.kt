package com.android.data.repository

import com.android.data.repository.datasource.DataSourceModule
import com.android.domain.repository.DetailRepository
import com.android.domain.repository.GithubRepositoriesRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

/**
 * Created by hassanalizadeh on 19,September,2020
 */
@Module(includes = [DataSourceModule::class])
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun provideRepositoriesRepository(
        repositoriesRepositoryImpl: GithubRepositoriesRepositoryImpl
    ): GithubRepositoriesRepository

    @Binds
    @Reusable
    abstract fun provideDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository

}
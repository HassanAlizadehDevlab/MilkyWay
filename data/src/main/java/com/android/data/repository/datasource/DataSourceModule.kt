package com.android.data.repository.datasource

import com.android.data.repository.datasource.detail.DetailDataSource
import com.android.data.repository.datasource.detail.SmartDetailDataSource
import com.android.data.repository.datasource.repositories.RepositoriesDataSource
import com.android.data.repository.datasource.repositories.SmartRepositoriesDataSource
import dagger.Binds
import dagger.Module
import dagger.Reusable

/**
 * Created by hassanalizadeh on 19,September,2020
 */
@Module
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun provideRepositoriesDataSource(
        smartRepositoriesDataSource: SmartRepositoriesDataSource
    ): RepositoriesDataSource

    @Binds
    @Reusable
    abstract fun provideDetailDataSource(
        smartDetailDataSource: SmartDetailDataSource
    ): DetailDataSource

}
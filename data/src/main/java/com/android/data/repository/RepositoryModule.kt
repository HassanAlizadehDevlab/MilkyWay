package com.android.data.repository

import com.android.data.repository.datasource.DataSourceModule
import dagger.Module

/**
 * Created by hassanalizadeh on 19,September,2020
 */
@Module(includes = [DataSourceModule::class])
abstract class RepositoryModule
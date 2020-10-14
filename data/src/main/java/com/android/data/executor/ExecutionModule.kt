package com.android.data.executor

import com.android.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

/**
 * Created by hassanalizadeh on 28,August,2020
 */
@Module
abstract class ExecutionModule {

    @Binds
    abstract fun threadExecutor(executor: JobExecutor): ThreadExecutor

}
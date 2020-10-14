package com.android.presentation.common.executor

import com.android.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module

/**
 * UI thread provider module.
 */
@Module
abstract class ExecutionModule {

    @Binds
    abstract fun postExecutionThread(uiThread: UIThread): PostExecutionThread

}
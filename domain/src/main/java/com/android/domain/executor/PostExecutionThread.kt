package com.android.domain.executor

import io.reactivex.Scheduler

/**
 * The [ThreadExecutor] is a contract for UI Threads.
 */
interface PostExecutionThread {
    fun scheduler(): Scheduler
}
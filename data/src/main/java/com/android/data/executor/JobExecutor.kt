package com.android.data.executor

import com.android.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Threading Configuration for whole the project.
 */
class JobExecutor @Inject constructor() : ThreadExecutor {

    private val threadFactory: ThreadFactory
    private val threadPoolExecutor: ThreadPoolExecutor
    private val workQueue: LinkedBlockingDeque<Runnable>

    init {
        threadFactory = JobThreadFactory()
        workQueue = LinkedBlockingDeque()
        threadPoolExecutor = ThreadPoolExecutor(
            INITIAL_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue,
            threadFactory
        )
    }

    override fun execute(runnable: Runnable?) {
        runnable ?: throw IllegalArgumentException("Runnable to execute cannot be null.")
        threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable?): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }
    }

    companion object {
        private const val THREAD_NAME = "james_webb_custom_thread_"
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        private const val KEEP_ALIVE_TIME = 10L
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

}
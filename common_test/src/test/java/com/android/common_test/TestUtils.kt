package com.android.common_test

import com.android.common.error.ErrorCode
import com.android.common.error.ErrorThrowable
import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.mapper.map
import com.android.data.entity.model.local.RepositoryEntity
import com.android.data.entity.model.remote.Contributor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.mockwebserver.MockResponse
import okio.ByteString.Companion.readByteString
import java.io.IOException
import java.util.concurrent.*

/**
 * Created by hassanalizadeh on 15,October,2020
 */
object TestUtils {

    fun fetchContributors(): List<Contributor> {
        val listType = object : TypeToken<List<Contributor>>() {}.type
        return Gson().fromJson(readFileToString("contributors.json"), listType) as List<Contributor>
    }

    fun getEndCursor(): String? {
        val result = readFileToString("repositories.json")
        return LoadRepositoriesQuery
            .builder()
            .build().parse(result.byteInputStream().readByteString(result.length))
            .data?.search()?.pageInfo()?.endCursor()
    }

    fun repositoriesSize() = loadRepositoriesFromDB()?.size

    fun loadRepositoriesFromDB(): List<RepositoryEntity>? {
        val result = readFileToString("repositories.json")
        return LoadRepositoriesQuery
            .builder()
            .build().parse(result.byteInputStream().readByteString(result.length))
            .data?.search()?.nodes()?.map {
                it as LoadRepositoriesQuery.AsRepository
                it.map()
            }
    }

    fun fetchRepositoriesFromRemote(): MockResponse {
        return mockResponse("repositories.json")
    }

    fun errorResponse(): MockResponse {
        return mockResponse("error.json")
    }

    private fun readFileToString(fileName: String): String {
        return javaClass.classLoader?.getResourceAsStream("json/$fileName")
            ?.bufferedReader().use { it?.readText().orEmpty() }
    }

    @Throws(IOException::class)
    fun mockResponse(fileName: String): MockResponse {
        return MockResponse().setChunkedBody(readFileToString("/$fileName"), 32)
    }

    fun immediateExecutor(): Executor {
        return Executor { command -> command.run() }
    }

    fun immediateExecutorService(): ExecutorService {
        return object : AbstractExecutorService() {
            override fun shutdown() = Unit

            override fun shutdownNow(): List<Runnable>? = null

            override fun isShutdown(): Boolean = false

            override fun isTerminated(): Boolean = false

            @Throws(InterruptedException::class)
            override fun awaitTermination(l: Long, timeUnit: TimeUnit): Boolean = false

            override fun execute(runnable: Runnable) = runnable.run()
        }
    }

    class TestExecutor : Executor {
        private val commands = ConcurrentLinkedQueue<Runnable>()

        override fun execute(command: Runnable) {
            commands.add(command)
        }

        fun triggerActions() {
            for (command in commands) {
                command.run()
            }
        }
    }

    fun error(): ErrorThrowable = ErrorThrowable(ErrorCode.ERROR_HAPPENED)

}
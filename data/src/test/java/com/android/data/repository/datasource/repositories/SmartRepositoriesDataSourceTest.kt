package com.android.data.repository.datasource.repositories

import com.android.common_test.IdFieldCacheKeyResolver
import com.android.common_test.TestUtils
import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.dao.RepositoriesDao
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@RunWith(JUnit4::class)
class SmartRepositoriesDataSourceTest {

    @get:Rule
    var server = MockWebServer()

    private lateinit var apolloClient: ApolloClient
    private lateinit var spyApolloClient: ApolloClient

    @Mock
    private lateinit var repositoriesDao: RepositoriesDao
    private lateinit var dataSource: RepositoriesDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val okHttpClient = OkHttpClient.Builder()
            .dispatcher(Dispatcher(TestUtils.immediateExecutorService()))
            .build()

        apolloClient = ApolloClient.builder()
            .serverUrl(server.url("/"))
            .okHttpClient(okHttpClient)
            .normalizedCache(
                LruNormalizedCacheFactory(EvictionPolicy.NO_EVICTION),
                IdFieldCacheKeyResolver()
            )
            .defaultResponseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
            .dispatcher(TestUtils.immediateExecutor())
            .build()

        spyApolloClient = spy(apolloClient)

        dataSource = Mockito.spy(
            SmartRepositoriesDataSource(
                spyApolloClient,
                repositoriesDao
            )
        )
    }

    @Test
    fun `load repositories from DB`() {
        // GIVEN
        Mockito.doReturn(Flowable.just(TestUtils.loadRepositoriesFromDB()))
            .whenever(repositoriesDao)
            .selectAll()

        // WHEN
        dataSource.loadRepositories()
            .test()
            .assertComplete()

        // THEN
        Mockito.verify(repositoriesDao).selectAll()
    }

    @Test
    fun `fetch repositories from server`() {
        // GIVEN
        server.enqueue(TestUtils.fetchRepositoriesFromRemote())

        // WHEN
        dataSource.fetchRepositories()
            .test()
            .assertComplete()

        // THEN
        verify(spyApolloClient).query<LoadRepositoriesQuery.Data, LoadRepositoriesQuery.Data, LoadRepositoriesQuery.Variables>(
            argThat { query ->
                query.variables().after().value == null
            })
        verify(repositoriesDao).insert(argThat {
            this.size == TestUtils.repositoriesSize()
        })
    }

    @Test
    fun `error on fetching repositories from server`() {
        // GIVEN
        server.enqueue(TestUtils.errorResponse())

        // WHEN
        dataSource.fetchRepositories()
            .test()
            .assertNotComplete()

        // THEN
        verify(repositoriesDao, never()).insert(any())
    }

    @Test
    fun `fetch more repositories from server`() {
        // GIVEN
        val after = TestUtils.getEndCursor()

        server.enqueue(TestUtils.fetchRepositoriesFromRemote())
        server.enqueue(TestUtils.fetchRepositoriesFromRemote())

        // WHEN
        dataSource.fetchRepositories()
            .andThen(Completable.fromCallable {
                reset(spyApolloClient)
                reset(repositoriesDao)
            })
            .andThen(dataSource.fetchMoreRepositories())
            .test()
            .assertComplete()

        // THEN
        verify(
            spyApolloClient
        ).query<LoadRepositoriesQuery.Data, LoadRepositoriesQuery.Data, LoadRepositoriesQuery.Variables>(
            argThat { query ->
                query.variables().after().value == after
            })
        verify(repositoriesDao).insert(argThat {
            this.size == TestUtils.repositoriesSize()
        })
    }

    @Test
    fun `get single repository by name`() {
        // GIVEN
        val reposName = "mojombo"
        val repository = TestUtils.loadRepositoriesFromDB()?.get(0)
        doReturn(Single.just(repository))
            .whenever(repositoriesDao).select(anyString())

        // WHEN
        dataSource.getRepository(reposName)
            .test()
            .assertValue(repository)
            .assertComplete()

        // THEN
        verify(repositoriesDao).select(anyString())
    }

}
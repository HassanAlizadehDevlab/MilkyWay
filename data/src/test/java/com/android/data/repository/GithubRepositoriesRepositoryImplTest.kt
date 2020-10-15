package com.android.data.repository

import com.android.common_test.TestUtils
import com.android.data.entity.mapper.map
import com.android.data.repository.datasource.repositories.RepositoriesDataSource
import com.android.domain.repository.GithubRepositoriesRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@RunWith(JUnit4::class)
class GithubRepositoriesRepositoryImplTest {

    @Mock
    private lateinit var dataSource: RepositoriesDataSource
    private lateinit var repository: GithubRepositoriesRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = GithubRepositoriesRepositoryImpl(dataSource)
    }

    @Test
    fun `load repositories`() {
        //GIVEN
        val hasNextPage = true
        val repositories = TestUtils.loadRepositoriesFromDB()
        doReturn(Flowable.just(Pair(repositories, hasNextPage)))
            .whenever(dataSource).loadRepositories()

        //WHEN
        repository.loadRepositories()
            .test()
            .assertValue {
                it.hasNextPage == hasNextPage
                it.repositories?.size == repositories?.size
            }
            .assertComplete()

        //THEN
        verify(dataSource).loadRepositories()
    }

    @Test
    fun `load comics`() {
        //GIVEN
        doReturn(Completable.complete()).whenever(dataSource).fetchRepositories()

        //WHEN
        repository.fetchRepositories()
            .test()
            .assertComplete()

        //THEN
        verify(dataSource).fetchRepositories()
    }

    @Test
    fun `load more comics`() {
        //GIVEN
        doReturn(Completable.complete()).whenever(dataSource).fetchMoreRepositories()

        //WHEN
        repository.fetchMoreRepositories()
            .test()
            .assertComplete()

        //THEN
        verify(dataSource).fetchMoreRepositories()
    }

    @Test
    fun `get repository by name`() {
        //GIVEN
        val repoName = "mojombo"
        val repo = TestUtils.loadRepositoriesFromDB()?.get(0)
        doReturn(Single.just(repo)).whenever(dataSource).getRepository(anyString())

        //WHEN
        repository.getRepositoryByName(repoName)
            .test()
            .assertValue(repo?.map())
            .assertComplete()

        //THEN
        verify(dataSource).getRepository(repoName)
    }
}
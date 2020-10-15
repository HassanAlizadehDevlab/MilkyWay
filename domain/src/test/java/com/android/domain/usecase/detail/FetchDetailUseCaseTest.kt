package com.android.domain.usecase.detail

import com.android.common.error.ErrorThrowable
import com.android.common_test.TestUtils
import com.android.common_test.transformer.TestSTransformer
import com.android.data.entity.mapper.map
import com.android.domain.entity.DetailObject
import com.android.domain.repository.DetailRepository
import com.android.domain.repository.GithubRepositoriesRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@RunWith(JUnit4::class)
class FetchDetailUseCaseTest {

    @Mock
    private lateinit var githubRepository: GithubRepositoriesRepository

    @Mock
    private lateinit var detailRepository: DetailRepository
    private lateinit var useCase: FetchDetailUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = FetchDetailUseCase(githubRepository, detailRepository, TestSTransformer())
    }


    @Test
    fun execute() {
        // GIVEN
        val ownerWithRepo = "adnan-wahab/regl-network-graph"
        val owner = ownerWithRepo.split("/")[0]
        val repo = ownerWithRepo.split("/")[1]
        val repository = TestUtils.loadRepositoriesFromDB()?.map()?.get(0)!!
        val contributors = TestUtils.fetchContributors().map()
        doReturn(Single.just(repository)).whenever(githubRepository)
            .getRepositoryByName(anyString())
        doReturn(Single.just(contributors)).whenever(detailRepository)
            .fetchContributors(anyString(), anyString())

        // WHEN
        useCase.invoke(ownerWithRepo)
            .test()
            .assertValue(DetailObject(repository, contributors))
            .assertComplete()

        // THEN
        verify(githubRepository).getRepositoryByName(ownerWithRepo)
        verify(detailRepository).fetchContributors(owner, repo)
    }


    @Test
    fun `execute on error`() {
        // GIVEN
        val ownerWithRepo = "adnan-wahab/regl-network-graph"
        val owner = ownerWithRepo.split("/")[0]
        val repo = ownerWithRepo.split("/")[1]
        val repository = TestUtils.loadRepositoriesFromDB()?.map()?.get(0)!!
        val contributors = TestUtils.fetchContributors().map()
        doReturn(Single.error<ErrorThrowable>(TestUtils.error())).whenever(githubRepository)
            .getRepositoryByName(anyString())
        doReturn(Single.just(contributors)).whenever(detailRepository)
            .fetchContributors(anyString(), anyString())

        // WHEN
        useCase.invoke(ownerWithRepo)
            .test()
            .assertNotComplete()

        // THEN
        verify(githubRepository).getRepositoryByName(ownerWithRepo)
        verify(detailRepository).fetchContributors(owner, repo)
    }


    @Test
    fun `execute on empty param`() {
        // GIVEN
        val ownerWithRepo = ""
        val owner = ""
        val repo = ""
        val repository = TestUtils.loadRepositoriesFromDB()?.map()?.get(0)!!
        val contributors = TestUtils.fetchContributors().map()
        doReturn(Single.just(repository)).whenever(githubRepository)
            .getRepositoryByName(anyString())
        doReturn(Single.just(contributors)).whenever(detailRepository)
            .fetchContributors(anyString(), anyString())

        // WHEN
        useCase.invoke(ownerWithRepo)
            .test()
            .assertNotComplete()

        // THEN
        verify(githubRepository, never()).getRepositoryByName(anyString())
        verify(detailRepository, never()).fetchContributors(anyString(), anyString())
    }


}
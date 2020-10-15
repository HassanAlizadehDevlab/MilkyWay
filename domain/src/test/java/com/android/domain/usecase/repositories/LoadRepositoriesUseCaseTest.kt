package com.android.domain.usecase.repositories

import com.android.common_test.TestUtils
import com.android.common_test.transformer.TestFTransformer
import com.android.data.entity.mapper.map
import com.android.domain.repository.GithubRepositoriesRepository
import com.android.domain.usecase.invoke
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@RunWith(JUnit4::class)
class LoadRepositoriesUseCaseTest {

    @Mock
    private lateinit var repository: GithubRepositoriesRepository
    private lateinit var useCase: LoadRepositoriesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = LoadRepositoriesUseCase(repository, TestFTransformer())
    }


    @Test
    fun execute() {
        // GIVEN
        val expect = TestUtils.loadRepositoriesFromDB()?.map()
        val hasNextPage = true
        Mockito.doReturn(Flowable.just(expect to hasNextPage)).whenever(repository)
            .loadRepositories()

        // WHEN
        useCase.invoke()
            .test()
            .assertComplete()

        // THEN
        Mockito.verify(repository).loadRepositories()
    }


}
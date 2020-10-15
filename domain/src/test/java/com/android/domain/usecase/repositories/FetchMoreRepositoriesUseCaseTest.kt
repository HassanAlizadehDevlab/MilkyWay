package com.android.domain.usecase.repositories

import com.android.common_test.transformer.TestCTransformer
import com.android.domain.repository.GithubRepositoriesRepository
import com.android.domain.usecase.invoke
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Assert.*
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
class FetchMoreRepositoriesUseCaseTest {

    @Mock
    private lateinit var repository: GithubRepositoriesRepository
    private lateinit var useCase: FetchMoreRepositoriesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = FetchMoreRepositoriesUseCase(repository, TestCTransformer())
    }


    @Test
    fun execute() {
        // GIVEN
        Mockito.doReturn(Completable.complete()).whenever(repository).fetchMoreRepositories()

        // WHEN
        useCase.invoke()
            .test()
            .assertComplete()

        // THEN
        Mockito.verify(repository).fetchMoreRepositories()
    }

}
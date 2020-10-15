package com.android.data.repository

import com.android.common_test.TestUtils
import com.android.data.repository.datasource.detail.DetailDataSource
import com.android.domain.repository.DetailRepository
import com.nhaarman.mockitokotlin2.doReturn
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
class DetailRepositoryImplTest {

    @Mock
    private lateinit var dataSource: DetailDataSource
    private lateinit var repository: DetailRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = DetailRepositoryImpl(dataSource)
    }

    @Test
    fun `fetch contributors`() {
        //GIVEN
        val owner = "mojombo"
        val repo = "grit"
        val perPage = 5
        doReturn(Single.just(TestUtils.fetchContributors()))
            .whenever(dataSource).fetchContributors(anyString(), anyString())

        //WHEN
        repository.fetchContributors(owner, repo)
            .test()
            .assertValue {
                it.size == perPage
            }
            .assertComplete()

        //THEN
        verify(dataSource).fetchContributors(owner, repo)
    }

}
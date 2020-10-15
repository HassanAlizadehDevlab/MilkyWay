package com.android.data.repository.datasource.detail

import com.android.common_test.TestUtils
import com.android.data.network.DataServiceContributors
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@RunWith(JUnit4::class)
class SmartDetailDataSourceTest {

    @Mock
    private lateinit var service: DataServiceContributors
    private lateinit var smartDetailDataSource: SmartDetailDataSource


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        smartDetailDataSource = Mockito.spy(
            SmartDetailDataSource(
                service
            )
        )
    }


    @Test
    fun `get contributors`() {
        // GIVEN
        val owner = "mojombo"
        val repo = "grit"
        val perPage = 5
        Mockito.doReturn(Single.just(mutableListOf(TestUtils.fetchContributors())))
            .whenever(service)
            .getContributors(anyString(), anyString(), anyInt())

        // WHEN
        smartDetailDataSource.fetchContributors(owner, repo)
            .test()
            .assertComplete()

        // THEN
        Mockito.verify(service).getContributors(owner, repo, perPage)
    }

}
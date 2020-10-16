package com.android.presentation.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.common_test.TestUtils
import com.android.common_test.observe
import com.android.data.entity.mapper.map
import com.android.domain.entity.ContributorObject
import com.android.domain.entity.DetailObject
import com.android.domain.entity.RepositoryObject
import com.android.domain.usecase.detail.FetchDetailUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fetchDetailUseCase: FetchDetailUseCase = mock()
    private lateinit var viewModel: DetailViewModel


    private fun createViewModel() {
        viewModel = DetailViewModel(
            fetchDetailUseCase
        )
    }

    @Test
    fun `load detail and show loading`() {
        // GIVEN
        dataExistsForGettingDetail()
        val nameWithOwner = "adnan-wahab/regl-network-graph"
        val loadingObserver = mock<(Boolean) -> Unit>()
        createViewModel()
        viewModel.isRefreshing.observe(loadingObserver)

        // WHEN
        viewModel.loadDetail(nameWithOwner)

        // THEN
        verify(loadingObserver).invoke(true)
        verify(loadingObserver).invoke(false)
    }

    @Test
    fun `load detail and check contributors were set`() {
        // GIVEN
        dataExistsForGettingDetail()
        val nameWithOwner = "adnan-wahab/regl-network-graph"
        val contributors = TestUtils.fetchContributors().map()
        val contributorObserver = mock<(List<ContributorObject>) -> Unit>()
        createViewModel()
        viewModel.contributors.observe(contributorObserver)

        // WHEN
        viewModel.loadDetail(nameWithOwner)

        // THEN
        verify(contributorObserver).invoke(contributors)
    }

    @Test
    fun `load detail and check repository was set`() {
        // GIVEN
        dataExistsForGettingDetail()
        val nameWithOwner = "adnan-wahab/regl-network-graph"
        val repository = TestUtils.loadRepositoriesFromDB()?.map()?.get(0)!!
        val repositoryObserver = mock<(RepositoryObject) -> Unit>()
        createViewModel()
        viewModel.repository.observe(repositoryObserver)

        // WHEN
        viewModel.loadDetail(nameWithOwner)

        // THEN
        verify(repositoryObserver).invoke(repository)
    }

    private fun dataExistsForGettingDetail() {
        val repository = TestUtils.loadRepositoriesFromDB()?.map()?.get(0)!!
        val contributors = TestUtils.fetchContributors().map()
        val detailObject = DetailObject(repository, contributors)
        Mockito.doReturn(Single.just(detailObject))
            .whenever(fetchDetailUseCase).invoke(anyString())
    }

}
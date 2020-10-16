package com.android.presentation.ui.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.common_test.TestUtils
import com.android.common_test.observe
import com.android.data.entity.mapper.map
import com.android.domain.entity.RepositoriesObject
import com.android.domain.entity.RepositoryObject
import com.android.domain.usecase.invoke
import com.android.domain.usecase.repositories.FetchMoreRepositoriesUseCase
import com.android.domain.usecase.repositories.FetchRepositoriesUseCase
import com.android.domain.usecase.repositories.LoadRepositoriesUseCase
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by hassanalizadeh on 16,October,2020
 */
@RunWith(JUnit4::class)
class RepositoryViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase = mock()
    private val fetchMoreRepositoriesUseCase: FetchMoreRepositoriesUseCase = mock()
    private val loadRepositoriesUseCase: LoadRepositoriesUseCase = mock()
    private lateinit var viewModel: RepositoryViewModel


    private fun createViewModel() {
        viewModel = RepositoryViewModel(
            fetchRepositoriesUseCase,
            fetchMoreRepositoriesUseCase,
            loadRepositoriesUseCase
        )
    }

    @Test
    fun `success on fetching repositories from remote with loading`() {
        // GIVEN
        dataExistsForRefreshRepositories()
        val loadingObserver = mock<(Boolean) -> Unit>()
        createViewModel()
        viewModel.isRefreshing.observe(loadingObserver)

        // WHEN
        viewModel.refresh()

        // THEN
        verify(loadingObserver).invoke(true)
        verify(fetchRepositoriesUseCase).invoke()
        verify(loadingObserver).invoke(false)
    }

    @Test
    fun `success on loading repositories from DB`() {
        // GIVEN
        dataExistsForLoadingRepositories()
        val repositoriesSize = TestUtils.repositoriesSize()
        val repositoriesObserver = mock<(List<RepositoryObject>?) -> Unit>()
        createViewModel()
        viewModel.repositories.observe(repositoriesObserver)

        // WHEN
        // It happens automatically

        // THEN
        verify(repositoriesObserver).invoke(argThat {
            this.size == repositoriesSize
        })
        assert(viewModel.hasNextPage)
    }

    private fun dataExistsForRefreshRepositories() {
        Mockito.doReturn(Completable.complete())
            .whenever(fetchRepositoriesUseCase).invoke()
    }

    private fun dataExistsForLoadingRepositories() {
        val hasNextPage = true
        Mockito.doReturn(
            Flowable.just(
                RepositoriesObject(
                    TestUtils.loadRepositoriesFromDB()?.map(),
                    hasNextPage
                )
            )
        ).whenever(loadRepositoriesUseCase).invoke()
    }

}
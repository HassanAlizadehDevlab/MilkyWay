package com.android.presentation.ui.repositories

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.android.domain.entity.RepositoriesObject
import com.android.domain.entity.RepositoryObject
import com.android.domain.usecase.invoke
import com.android.domain.usecase.repositories.FetchMoreRepositoriesUseCase
import com.android.domain.usecase.repositories.FetchRepositoriesUseCase
import com.android.domain.usecase.repositories.LoadRepositoriesUseCase
import com.android.presentation.adapter.BaseAction
import com.android.presentation.adapter.LoadMoreState
import com.android.presentation.common.extension.map
import com.android.presentation.common.view.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class RepositoryViewModel @Inject constructor(
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase,
    private val fetchMoreRepositoriesUseCase: FetchMoreRepositoriesUseCase,
    private val loadRepositoriesUseCase: LoadRepositoriesUseCase
) : BaseViewModel() {

    private val _repositories: LiveData<RepositoriesObject> =
        LiveDataReactiveStreams.fromPublisher(loadRepositoriesUseCase.invoke())

    val repositories: LiveData<MutableList<RepositoryObject>?> = _repositories.map {
        hasNextPage = it.hasNextPage
        it.repositories?.toMutableList()
    }
    val isRefreshing = MutableLiveData<Boolean>()
    val clickObservable = MutableLiveData<BaseAction>()

    @VisibleForTesting
    var hasNextPage: Boolean = true


    fun refresh() {
        isRefreshing.value = true
        fetchRepositoriesUseCase.invoke()
            .doOnEvent { isRefreshing.value = false }
            .onError()
            .subscribe()
            .track()
    }

    fun loadMoreObserver(loadMoreObservable: PublishSubject<LoadMoreState>) {
        loadMoreObservable.subscribe {
            if (it == LoadMoreState.LOAD)
                if (!hasNextPage)
                    loadMoreObservable.onNext(LoadMoreState.FINISH)
                else
                    loadMoreVenues { loadMoreObservable.onNext(LoadMoreState.NOT_LOAD) }
        }.track()
    }

    private fun loadMoreVenues(onNext: () -> Unit) {
        fetchMoreRepositoriesUseCase.invoke()
            .doOnEvent { onNext.invoke() }
            .onError()
            .subscribe()
            .track()
    }

    /**
     * @param actions contains all of the action we have for each ad items in view
     * */
    fun observeClicks(actions: Observable<BaseAction>) {
        actions.subscribe {
            clickObservable.value = it
        }.track()
    }

}
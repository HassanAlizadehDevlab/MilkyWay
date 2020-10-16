package com.android.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import com.android.domain.entity.DetailObject
import com.android.domain.usecase.detail.FetchDetailUseCase
import com.android.presentation.common.extension.map
import com.android.presentation.common.view.BaseViewModel
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class DetailViewModel @Inject constructor(
    private val fetchDetailUseCase: FetchDetailUseCase
) : BaseViewModel() {

    private val _detail = MutableLiveData<DetailObject>()
    val repository = _detail.map { it.repository }
    val contributors = _detail.map { it.contributors }
    val isRefreshing = MutableLiveData<Boolean>()


    fun loadDetail(nameWithOwner: String) {
        isRefreshing.value = true
        fetchDetailUseCase.invoke(nameWithOwner)
            .doOnEvent { _, _ -> isRefreshing.value = false }
            .subscribe({ detail ->
                _detail.value = detail
            }, {})
            .track()
    }

}
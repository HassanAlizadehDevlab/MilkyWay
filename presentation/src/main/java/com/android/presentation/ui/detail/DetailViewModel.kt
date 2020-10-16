package com.android.presentation.ui.detail

import com.android.domain.usecase.detail.FetchDetailUseCase
import com.android.presentation.common.view.BaseViewModel
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class DetailViewModel @Inject constructor(
    private val fetchDetailUseCase: FetchDetailUseCase
): BaseViewModel() {



}
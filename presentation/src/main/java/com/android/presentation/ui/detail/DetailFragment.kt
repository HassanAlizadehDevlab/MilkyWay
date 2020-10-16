package com.android.presentation.ui.detail

import com.android.presentation.common.view.BaseFragment

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class DetailFragment : BaseFragment() {

    companion object {
        private const val PARAM = "param"
        fun newInstance(nameWithOwner: String) = DetailFragment().apply {
            arguments?.putString(PARAM, nameWithOwner)
        }
    }

}
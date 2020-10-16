package com.android.presentation.common.navigator

import androidx.fragment.app.FragmentManager
import com.android.presentation.R
import com.android.presentation.common.extension.addFragment
import com.android.presentation.common.view.BaseActivityModule
import com.android.presentation.ui.detail.DetailFragment
import com.android.presentation.ui.repositories.RepositoryFragment
import javax.inject.Inject
import javax.inject.Named

/**
 * Handle all navigation in here.
 *
 * @param fragmentManager is [MainActivity]'s fragmentManager.
 */
class Navigator @Inject constructor(
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    private val fragmentManager: FragmentManager
) {

    fun showRepositories() {
        fragmentManager.addFragment(
            containerViewId = R.id.fragmentContainer,
            fragment = RepositoryFragment.newInstance()
        )
    }

    fun showDetail(nameWithOwner: String) {
        fragmentManager.addFragment(
            containerViewId = R.id.fragmentContainer,
            fragment = DetailFragment.newInstance(nameWithOwner),
            tag = DetailFragment::class.java.name
        )
    }

}
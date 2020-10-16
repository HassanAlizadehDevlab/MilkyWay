package com.android.presentation.ui.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.domain.entity.RepositoryObject
import com.android.presentation.R
import com.android.presentation.adapter.BaseAction
import com.android.presentation.common.extension.linearLayout
import com.android.presentation.common.extension.observe
import com.android.presentation.common.extension.viewModelProvider
import com.android.presentation.common.utils.Utils
import com.android.presentation.common.view.BaseFragment
import com.android.presentation.common.viewmodel.ViewModelProviderFactory
import com.android.presentation.ui.repositories.adapter.RepositoryAdapter
import kotlinx.android.synthetic.main.fragment_repositories.*
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class RepositoryFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(factory)
        adapter = RepositoryAdapter { holder ->
            viewModel.observeClicks(holder.observe())
        }

        viewModel.loadMoreObserver(adapter.getLoadMoreObservable())
        observe(viewModel.repositories, ::observeRepositories)
        observe(viewModel.isRefreshing, ::observeRefreshing)
        observe(viewModel.clickObservable, ::observeActions)
        observe(viewModel.messageObservable, ::showMessage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_repositories, container, false)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupSwipeRefreshLayout()
        setupRecyclerView()
        setupAdapter()

        if (savedInstanceState == null)
            onRefresh()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    private fun setupRecyclerView() {
        recyclerView?.linearLayout(
            context = activityContext,
            spacing = Utils.convertPxToDp(activityContext, 8f).toInt()
        )
    }

    private fun setupAdapter() {
        recyclerView?.adapter = adapter
    }

    private fun setupSwipeRefreshLayout() {
        loadingIndicator.setOnRefreshListener(this)
        loadingIndicator?.setColorSchemeColors(
            ContextCompat.getColor(activityContext, R.color.colorAccent)
        )
    }

    private fun observeRepositories(repositories: MutableList<RepositoryObject>?) {
        repositories?.let {
            adapter.addItems(repositories)
        }
    }

    private fun observeActions(actions: BaseAction) {
        when (actions) {
            else -> {
            }
        }
    }

    private fun observeRefreshing(status: Boolean) {
        if (status) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun hideLoading() {
        if (loadingIndicator.isRefreshing)
            loadingIndicator.post { loadingIndicator?.isRefreshing = false }
    }

    private fun showLoading() {
        if (!loadingIndicator.isRefreshing)
            loadingIndicator.post { loadingIndicator?.isRefreshing = true }
    }

    companion object {
        fun newInstance(): RepositoryFragment = RepositoryFragment()
    }

}
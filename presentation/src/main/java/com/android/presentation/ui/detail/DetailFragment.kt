package com.android.presentation.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import com.android.domain.entity.ContributorObject
import com.android.domain.entity.RepositoryObject
import com.android.presentation.R
import com.android.presentation.common.extension.observe
import com.android.presentation.common.extension.viewModelProvider
import com.android.presentation.common.extension.visibleOrGone
import com.android.presentation.common.view.BaseFragment
import com.android.presentation.common.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

/**
 * Created by hassanalizadeh on 16,October,2020
 */
class DetailFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelProvider(factory)


        observe(viewModel.repository, ::observeRepository)
        observe(viewModel.contributors, ::observeContributors)
        observe(viewModel.isRefreshing, ::observeRefreshing)
        observe(viewModel.messageObservable, ::showMessage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_detail, container, false)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        viewModel.loadDetail(nameWithOwnerParameter.invoke())
    }

    private fun observeRepository(repository: RepositoryObject) {
        with(repository) {
            txtName.text = nameWithOwner
            txtDateTime.text = createdAt

            txtStarCount.text = SpannableStringBuilder()
                .bold { this.append(getString(R.string.stargazer_count)) }
                .append(stargazerCount.toString())

            txtForkCount.text = SpannableStringBuilder()
                .bold { this.append(getString(R.string.fork_count)) }
                .append(forkCount.toString())

            txtUrl.text = SpannableStringBuilder()
                .bold { this.append(getString(R.string.url)) }
                .append(url)

            txtDescription.text = SpannableStringBuilder()
                .bold { this.append(getString(R.string.description)) }
                .append(url)
        }
    }

    private fun observeContributors(contributors: List<ContributorObject>) {
        contributors.joinToString(separator = ", \n", prefix = "\n") { it.login }.let {
            txtContributors.text = SpannableStringBuilder()
                .bold { this.append(getString(R.string.contributors)) }
                .append(it)
        }
    }

    private fun observeRefreshing(status: Boolean) {
        loadingIndicator.visibleOrGone(status)
    }

    private val nameWithOwnerParameter = { arguments?.getString(PARAM).orEmpty() }

    companion object {
        private const val PARAM = "param"
        fun newInstance(nameWithOwner: String) = DetailFragment().apply {
            arguments = Bundle().apply { putString(PARAM, nameWithOwner) }
        }
    }

}
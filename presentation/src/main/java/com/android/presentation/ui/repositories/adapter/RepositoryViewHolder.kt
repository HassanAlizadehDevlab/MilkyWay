package com.android.presentation.ui.repositories.adapter

import android.view.View
import com.android.domain.entity.DomainObject
import com.android.domain.entity.RepositoryObject
import com.android.presentation.adapter.BaseViewHolder
import com.android.presentation.adapter.ViewTypeHolder
import kotlinx.android.synthetic.main.adapter_repository.view.*

/**
 * Created by hassanalizadeh on 19,September,2020
 */
class RepositoryViewHolder(
    private val containerView: View
) : BaseViewHolder<RepositoryObject>(containerView) {

    override fun getType(): Int = ViewTypeHolder.REPOSITORY_VIEW

    override fun bind(data: DomainObject?) {
        data ?: return
        if (data !is RepositoryObject) return

        // Set data to view
        containerView.txtName.text = data.nameWithOwner
        containerView.txtDateTime.text = data.createdAt

        // Handle click listener
        containerView.root.setOnClickListener {
            mSubject.onNext(ViewRepositoryAction(data.nameWithOwner))
        }
    }
}
package com.android.presentation.common.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hassanalizadeh on 17,September,2020
 */
object AnimationUtil {

    fun animateItems(holder: RecyclerView.ViewHolder, loadMore: Boolean) {
        val animatorSet = AnimatorSet()

        val animatorTranslateY: ObjectAnimator =
            ObjectAnimator.ofFloat(
                holder.itemView,
                "translationY",
                if (loadMore) 500f else -500f,
                0f
            )
        animatorTranslateY.duration = 500
        animatorSet.play(animatorTranslateY)
        animatorSet.start()
    }
}
package com.android.presentation.ui

import android.os.Bundle
import com.android.presentation.R
import com.android.presentation.common.view.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            navigator.showRepositories()
    }
}
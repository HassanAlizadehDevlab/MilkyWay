package com.android.milkyway

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by hassanalizadeh on 14,October,2020
 */
class MilkyWay : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerMilkyWayComponent.factory().create(this)


    override fun onCreate() {
        super.onCreate()
        initRxErrorHandler()
    }

    /**
     * RxJavaPlugins.setErrorHandler used for handle rx errors like network errors
     * */
    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {}
    }
}
package com.android.milkyway

import com.android.data.DataModule
import com.android.domain.DomainModule
import com.android.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Project dagger component.
 */
@Singleton
@Component(
    modules = [
        MilkyWayModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
interface MilkyWayComponent : AndroidInjector<MilkyWay> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MilkyWay): MilkyWayComponent
    }

}
package com.android.data.entity

import android.app.Application
import androidx.room.Room
import com.android.data.entity.dao.RepositoriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@Module
class EntityModule {

    @Provides
    fun repositoriesDao(db: MilkyWayDatabase): RepositoriesDao = db.repositoriesDao()

    @Provides
    @Singleton
    fun database(application: Application): MilkyWayDatabase = Room.databaseBuilder(
        application.applicationContext,
        MilkyWayDatabase::class.java,
        "milky_way_db"
    ).build()
}
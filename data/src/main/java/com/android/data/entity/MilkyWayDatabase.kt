package com.android.data.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.data.entity.dao.RepositoriesDao
import com.android.data.entity.model.local.RepositoryEntity

/**
 * The Milky Way's Database.
 */
@Database(
    entities = [
        RepositoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MilkyWayDatabase : RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao

}
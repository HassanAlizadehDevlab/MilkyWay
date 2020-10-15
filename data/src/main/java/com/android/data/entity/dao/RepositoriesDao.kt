package com.android.data.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.data.entity.model.local.RepositoryEntity
import io.reactivex.Flowable

/**
 * Created by hassanalizadeh on 15,October,2020
 */
@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repository")
    fun selectAll(): Flowable<List<RepositoryEntity>>

    @Query("DELETE FROM repository")
    fun deleteAll()

}
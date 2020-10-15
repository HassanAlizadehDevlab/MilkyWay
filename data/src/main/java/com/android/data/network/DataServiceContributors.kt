package com.android.data.network

import com.android.data.entity.model.remote.Contributor
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hassanalizadeh on 15,October,2020
 */
interface DataServiceContributors {
    @GET("/repos/{login}/{name}/contributors")
    fun getContributors(
        @Path("login") owner: String,
        @Path("name") repo: String,
        @Query("per_page") perPage: Int = 5
    ): Single<List<Contributor>>
}
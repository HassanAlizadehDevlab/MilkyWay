package com.android.data.entity.mapper

import android.annotation.SuppressLint
import com.android.data.LoadRepositoriesQuery
import com.android.data.entity.model.local.RepositoryEntity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by hassanalizadeh on 15,October,2020
 */
fun LoadRepositoriesQuery.AsRepository.map() = RepositoryEntity(
    nameWithOwner = nameWithOwner(),
    createdAt = DateUtil.formatDate(createdAt().toString()),
    stargazerCount = stargazerCount(),
    forkCount = forkCount(),
    description = description().toString(),
    url = url().toString()
)

object DateUtil {
    private const val OLD_FORMAT = "yyyy-MM-ddTHH:mm:ss"
    private val NEW_FORMAT = "yyyy-MM-dd"
    @SuppressLint("SimpleDateFormat")
    private val dt = SimpleDateFormat(OLD_FORMAT)

    fun formatDate(dateTime: String): String {
        val date = dt.parse(dateTime)
        dt.applyPattern(NEW_FORMAT)
        return dt.format(date)
    }
}
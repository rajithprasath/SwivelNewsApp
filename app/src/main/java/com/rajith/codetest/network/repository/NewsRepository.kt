package com.rajith.codetest.network.repository

import androidx.paging.DataSource
import com.rajith.codetest.database.entity.NewsEntity
import com.rajith.codetest.model.News


interface NewsRepository {
    suspend fun getNewsFromApi(
        country: String?,
        category: String?,
        page: Int,
        pageSize: Int,
        onSuccessAction: () -> Unit
    ): List<NewsEntity>

    suspend fun getNewsFromApiByPhrase(
        country: String?,
        category: String?,
        page: Int,
        pageSize: Int,
        onSuccessAction: () -> Unit
    ): List<NewsEntity>

    suspend fun insertNews(news: List<NewsEntity>)

    suspend fun findAllNews(
        country: String?,
        category: String?,
        page: Int,
        pageSize: Int,
        onSuccessAction: () -> Unit
    )
    suspend fun findAllNewsByPhrase(
        country: String?,
        category: String?,
        page: Int,
        pageSize: Int,
        onSuccessAction: () -> Unit
    )

    fun getNewsFactory(country: String?, category: String?): DataSource.Factory<Int, News>
}



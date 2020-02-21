package com.rajith.codetest.model

import androidx.paging.DataSource
import com.rajith.codetest.database.dao.NewsDao
import com.rajith.codetest.database.entity.NewsEntity
import com.rajith.codetest.dispatcher.CoroutineDispatcherProvider
import com.rajith.codetest.mapper.NewsMapper
import com.rajith.codetest.network.repository.NewsRepository
import com.rajith.codetest.network.source.NewsRemoteDataSource
import com.rajith.codetest.util.Result


import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcherProvider,
    private val mapper: NewsMapper,
    private val dao: NewsDao,
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsFromApi(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit): List<NewsEntity> {
        val apiResult = remoteDataSource.getNews(dispatcher.io, country, category, page, pageSize)
        if (apiResult is Result.Success) {
            onSuccessAction()
            return mapper.map(country, category, apiResult.data)
        }

        return emptyList()
    }

    override suspend fun getNewsFromApiByPhrase(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction: () -> Unit): List<NewsEntity> {
        val apiResult = remoteDataSource.getNewsByPhrase(dispatcher.io, country, category, page, pageSize)
        if (apiResult is Result.Success) {
            onSuccessAction()
            return mapper.map(country, category, apiResult.data)
        }

        return emptyList()
    }

    override suspend fun insertNews(news: List<NewsEntity>) {
        dao.insertAll(news)
    }


    override suspend fun findAllNews(country: String?, category : String?, page: Int, pageSize: Int, onSuccessAction: () -> Unit) {
        val result = getNewsFromApi(country, category, page, pageSize, onSuccessAction)
        insertNews(result)
    }

    override suspend fun findAllNewsByPhrase(country: String?, category: String?, page: Int, pageSize: Int, onSuccessAction: () -> Unit){
        val result = getNewsFromApiByPhrase(country, category, page, pageSize, onSuccessAction)
        insertNews(result)
    }

    override fun getNewsFactory(country: String?, category: String?): DataSource.Factory<Int, News> {
        if (category.isNullOrEmpty()) {
            return dao.findByCountry(country ?: "").map { mapper.mapToDomain(it) }
        }
        return dao.findByCategory(category).map { mapper.mapToDomain(it) }
    }
}
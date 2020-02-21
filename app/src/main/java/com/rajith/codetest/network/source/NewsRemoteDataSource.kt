package com.rajith.codetest.network.source


import com.rajith.codetest.network.RemoteDataSource
import com.rajith.codetest.network.response.NewsDto
import com.rajith.codetest.network.service.NewsService
import kotlinx.coroutines.CoroutineDispatcher
import com.rajith.codetest.util.Result
class NewsRemoteDataSource (private val newsService: NewsService) : RemoteDataSource() {

    suspend fun getNews(dispatcher: CoroutineDispatcher, country : String?, category : String?, page : Int, pageSize : Int): Result<NewsDto> {
        return safeApiCall(dispatcher) { newsService.getNews(country, category, page, pageSize) }
    }

    suspend fun getNewsByPhrase(dispatcher: CoroutineDispatcher, country : String?, category : String?, page : Int, pageSize : Int): Result<NewsDto> {
        return safeApiCall(dispatcher) { newsService.getNewsByPhrase(country, category, page, pageSize) }
    }

}
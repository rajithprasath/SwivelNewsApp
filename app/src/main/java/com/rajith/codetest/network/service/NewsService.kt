package com.rajith.codetest.network.service

import com.rajith.codetest.network.response.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsDto

    @GET("v2/everything")
    suspend fun getNewsByPhrase(
        @Query("country") country: String?,
        @Query("q") category: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsDto
}
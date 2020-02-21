package com.rajith.codetest.mapper

import com.rajith.codetest.database.entity.NewsEntity
import com.rajith.codetest.model.News
import com.rajith.codetest.network.response.NewsDto

class NewsMapper {

    fun map(country: String?, category: String?, input: NewsDto): List<NewsEntity> {
        val headlines = mutableListOf<NewsEntity>()
        input.articles?.map {
            headlines.add(
                NewsEntity(
                    it?.title ?: "",
                    it?.url ?: "",
                    it?.urlToImage ?: "",
                    country ?: "",
                    category ?: "",
                    it?.publishedAt ?: "",
                    it?.source?.id ?: "",
                    it?.source?.name ?: "",
                    it?.content ?: "",
                    it?.author ?: "",
                    it?.description ?: ""
                )
            )
        }
        return headlines
    }

    fun mapToDomain(input: NewsEntity): News {
        return News(
            input.title,
            input.url,
            input.imageUrl,
            input.publishedAt,
            input.category,
            input.sourceId,
            input.sourceName,
            input.content,
            input.author,
            input.description
        )
    }
}
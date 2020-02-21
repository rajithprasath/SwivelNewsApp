package com.rajith.codetest.mapper

import com.rajith.codetest.model.News
import com.rajith.codetest.util.toLocalTime
import javax.inject.Inject

class NewsPresentationMapper @Inject constructor(): Mapper<News, News>(){

    override fun map(input: News) : News {
        return News(input.title, input.url, input.imageUrl, input.publishedAt.toLocalTime(), input.category, input.sourceId, input.sourceName,input.content,input.author,input.description)
    }

}
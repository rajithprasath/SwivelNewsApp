package com.rajith.codetest.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String = "",
    @ColumnInfo(name = "country")
    var country: String = "",
    @ColumnInfo(name = "category")
    var category: String = "",
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String = "",
    @ColumnInfo(name = "source_id")
    var sourceId: String = "",
    @ColumnInfo(name = "source_name")
    var sourceName: String = "",
    @ColumnInfo(name = "content")
    var content: String = "",
    @ColumnInfo(name = "author")
    var author: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""
)
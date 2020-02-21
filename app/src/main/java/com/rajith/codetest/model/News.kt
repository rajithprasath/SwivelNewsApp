package com.rajith.codetest.model

import java.io.Serializable

 data class News(val title: String, val url : String, val imageUrl: String, val publishedAt : String, val category : String, val sourceId : String, val sourceName : String,val content : String,val author : String,val description : String) : Serializable
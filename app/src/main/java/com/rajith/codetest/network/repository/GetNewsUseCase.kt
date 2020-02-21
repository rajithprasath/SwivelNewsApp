package com.rajith.codetest.network.repository

import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend fun execute(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
         repository.findAllNews(country, category, page, pageSize, onSuccessAction)
    }

    suspend fun executeByPhrase(country : String?, category : String?, page : Int, pageSize : Int, onSuccessAction : () -> Unit) {
        repository.findAllNewsByPhrase(country, category, page, pageSize, onSuccessAction)
    }

    fun getNewsFactory(country : String?, category : String?) = repository.getNewsFactory(country, category)


}
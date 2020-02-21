package com.rajith.codetest.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.rajith.codetest.mapper.NewsPresentationMapper
import com.rajith.codetest.model.SearchQuery
import com.rajith.codetest.model.SearchResult
import com.rajith.codetest.network.repository.GetNewsUseCase
import com.rajith.codetest.network.repository.NewsBoundaryCallback

import javax.inject.Inject

class TopHeadlineViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val mapper: NewsPresentationMapper
) : ViewModel() {

    class Factory @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase,
        private val mapper: NewsPresentationMapper
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopHeadlineViewModel::class.java)) {
                return TopHeadlineViewModel(
                    getNewsUseCase,
                    mapper
                ) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }

    private val _category = MutableLiveData<SearchQuery>()

    private val searchResult = Transformations.map(_category) {
        search(it.country, it.category)
    }

    val news = Transformations.switchMap(searchResult) { it.news }
    val initialLoadingState = Transformations.switchMap(searchResult) { it.initialLoadingState }
    val searchState = Transformations.switchMap(searchResult) { it.searchState }

    fun setSearchQuery(country: String?, category: String?) {
        if (country.equals("null", true)) {
            _category.postValue(SearchQuery(null, category))
        } else {
            _category.postValue(SearchQuery(country, category))
        }
    }

    private fun search(country: String?, category: String?): SearchResult {
        val factory = getNewsUseCase.getNewsFactory(country, category).map { mapper.map(it) }
        val boundaryCallback =
            NewsBoundaryCallback(
                country,
                category,
                getNewsUseCase,
                viewModelScope
            )

        val newsSourceState = boundaryCallback.newsState
        val initialLoadingState = boundaryCallback.initialLoading
        val newsSource = LivePagedListBuilder(factory,
            PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

        return SearchResult(
            newsSourceState,
            initialLoadingState,
            newsSource
        )
    }
}
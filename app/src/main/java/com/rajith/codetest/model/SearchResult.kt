package com.rajith.codetest.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rajith.codetest.util.UiState


data class SearchResult(val searchState : LiveData<UiState>, val initialLoadingState : LiveData<UiState>, val news : LiveData<PagedList<News>> )
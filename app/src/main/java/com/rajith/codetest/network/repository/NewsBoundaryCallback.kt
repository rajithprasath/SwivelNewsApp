package com.rajith.codetest.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.rajith.codetest.model.News
import com.rajith.codetest.util.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsBoundaryCallback(
    private val country: String?,
    private val category: String?,
    private val getNewsUseCase: GetNewsUseCase,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<News>() {

    private val _newsState = MutableLiveData<UiState>()
    val newsState: LiveData<UiState> = _newsState

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private val pageSize = 5

    private val _initialLoading = MutableLiveData<UiState>(UiState.Loading)
    val initialLoading: LiveData<UiState>
        get() = _initialLoading

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: News) {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        setState(UiState.Loading)

        scope.launch(CoroutineExceptionHandler { _, _ -> onFetchNewsError() }) {
            if (category != null) {
                getNewsUseCase.executeByPhrase(
                    country,
                    category,
                    lastRequestedPage,
                    pageSize,
                    onSuccessAction = {
                        isRequestInProgress = false
                        lastRequestedPage++
                        setState(UiState.Success)
                    })
            } else {
                getNewsUseCase.execute(
                    country,
                    category,
                    lastRequestedPage,
                    pageSize,
                    onSuccessAction = {
                        isRequestInProgress = false
                        lastRequestedPage++
                        setState(UiState.Success)
                    })
            }


            setState(UiState.Complete)
            setInitialLoadingState(UiState.Complete)
        }

    }

    private fun onFetchNewsError() {
        isRequestInProgress = false
        setState(UiState.Error)
    }

    private fun setState(result: UiState) {
        _newsState.postValue(result)
    }

    private fun setInitialLoadingState(state: UiState) {
        _initialLoading.postValue(state)
    }
}
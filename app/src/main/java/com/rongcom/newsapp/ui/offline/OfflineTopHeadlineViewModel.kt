package com.rongcom.newsapp.ui.offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity
import com.rongcom.newsapp.data.model.topheadlines.toTopHeadlineEntity
import com.rongcom.newsapp.data.repository.OfflineTopHeadlineRepository
import com.rongcom.newsapp.ui.base.UiState
import com.rongcom.newsapp.ui.utils.AppConstant
import com.rongcom.newsapp.ui.utils.DispatcherProvider
import com.rongcom.newsapp.ui.utils.NetworkHelper
import com.rongcom.newsapp.ui.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineTopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: OfflineTopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger
) : ViewModel() {
    private val _topHeadlineUiState = MutableStateFlow<UiState<List<TopHeadlineEntity>>>(UiState.Loading)
    val topHeadlineUiState: StateFlow<UiState<List<TopHeadlineEntity>>> = _topHeadlineUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        startFetchingTopHeadlines()
    }

    fun startFetchingTopHeadlines() {
        if (checkInternetConnection()) {
            fetchTopHeadlines()
        } else {
            fetchTopHeadlinesFromDB()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchTopHeadlines() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }.map {
                    it.map { apiArticle -> apiArticle.toTopHeadlineEntity(AppConstant.COUNTRY) }
                }.flatMapConcat {
                    flow {
                        emit(
                            topHeadlineRepository.deleteAndInsertAllTopHeadlinesArticles(
                                it,
                                AppConstant.COUNTRY
                            )
                        )
                    }
                }.flowOn(dispatcherProvider.io).catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }.collect {
                    fetchTopHeadlinesFromDB()
                }
        }
    }

    private fun fetchTopHeadlinesFromDB() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlinesFromDB(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }
                .collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _topHeadlineUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _topHeadlineUiState.value = UiState.Success(it)
                        logger.d("OfflineTopHeadlineViewModel", "Success")
                    }
                }
        }
    }

}
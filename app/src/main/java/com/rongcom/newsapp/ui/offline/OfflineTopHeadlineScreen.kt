package com.rongcom.newsapp.ui.offline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rongcom.newsapp.data.local.entity.TopHeadlineEntity
import com.rongcom.newsapp.ui.base.ArticleList
import com.rongcom.newsapp.ui.base.ShowError
import com.rongcom.newsapp.ui.base.ShowLoading
import com.rongcom.newsapp.ui.base.UiState

@Composable
fun OfflineTopHeadlineRoute(
    onNewsClick: (url: String) -> Unit,
    topHeadlineViewModel: OfflineTopHeadlineViewModel = hiltViewModel()
){
    val newsUiState: UiState<List<TopHeadlineEntity>> by topHeadlineViewModel.topHeadlineUiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        TopOffHeadlineScreen(newsUiState, onNewsClick, onRetryClick = {
            topHeadlineViewModel.startFetchingTopHeadlines()
        })
    }
}

@Composable
fun TopOffHeadlineScreen(
    uiState: UiState<List<TopHeadlineEntity>>,
    onNewsClick: (url: String) -> Unit,
    onRetryClick: () -> Unit
){
    when(uiState) {
        is UiState.Success -> {
           ArticleList(articles = uiState.data, onNewsClick)
        }

        is UiState.Error -> {
          ShowError(text = uiState.message) {
              onRetryClick()
          }
        }
        UiState.Loading -> {
            ShowLoading()
        }
    }
}
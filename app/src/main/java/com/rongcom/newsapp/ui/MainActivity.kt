package com.rongcom.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rongcom.newsapp.ui.base.GlobalTopBar
import com.rongcom.newsapp.ui.base.NewsNavHost
import com.rongcom.newsapp.ui.base.rememberHeadlineHubAppState
import com.rongcom.newsapp.ui.theme.NewsAppTheme
import com.rongcom.newsapp.ui.theme.gray40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appState = rememberHeadlineHubAppState(navController = navController)

            NewsAppTheme {
                Scaffold(
                    topBar = {
                        GlobalTopBar(
                            title = appState.showScreenTitle,
                            shouldShowBackIcon = appState.shouldShowBackIcon,
                            onBackClick = navController::popBackStack
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .background(gray40),
                    ) {
                        NewsNavHost(navController)
                    }
                }
            }
        }
    }
}

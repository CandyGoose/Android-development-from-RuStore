package com.example.myapplication.presentation.appdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myapplication.appdetails.AppDetailsContent
import com.example.myapplication.appdetails.AppDetailsState

@Composable
fun AppDetailsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var descriptionCollapsed by remember { mutableStateOf(false) }

    when (val s = state) {
        AppDetailsState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AppDetailsState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Не удалось загрузить приложение")
            }
        }

        is AppDetailsState.Content -> {
            val contentState = s.copy(descriptionCollapsed = descriptionCollapsed)
            AppDetailsContent(
                content = contentState,
                onBackClick = { navController.navigateUp() },
                onShareClick = { },
                onWishlistClick = viewModel::toggleWishlist,
                onInstallClick = { },
                onReadMoreClick = { descriptionCollapsed = true },
                onDeveloperClick = { },
                modifier = modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
                    .verticalScroll(rememberScrollState())
            )
        }
    }
}

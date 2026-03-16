package com.example.myapplication.presentation.appdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import com.example.myapplication.appdetails.AppDetailsContent
import com.example.myapplication.appdetails.AppDetailsState
import com.example.myapplication.domain.AppRepository

@Composable
fun AppDetailsScreen(
    appId: String,
    navController: NavController,
    appRepository: AppRepository,
    modifier: Modifier = Modifier
) {
    val appDetails = appRepository.getAppById(appId)
    var descriptionCollapsed by remember { mutableStateOf(false) }

    if (appDetails == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Приложение не найдено")
        }
        return
    }

    val contentState = AppDetailsState.Content(
        appDetails = appDetails,
        descriptionCollapsed = descriptionCollapsed
    )

    AppDetailsContent(
        content = contentState,
        onBackClick = { navController.navigateUp() },
        onShareClick = { },
        onInstallClick = { },
        onReadMoreClick = { descriptionCollapsed = true },
        onDeveloperClick = { },
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    )
}

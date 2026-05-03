package com.example.myapplication.presentation.applist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.AppRepository
import com.example.myapplication.ui.theme.RuStoreBlue
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppListScreen(
    onAppClick: (String) -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
    appRepository: AppRepository,
    viewModelFactory: AppListViewModelFactory = AppListViewModelFactory(appRepository),
    viewModel: AppListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = viewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is AppListEvent.ShowLogoSnackbar -> onShowMessage(event.message)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RuStoreBlue)
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        RuStoreHeader(
            onLogoClick = { viewModel.onLogoClick() }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp)
            ) {
                items(
                    items = uiState.apps,
                    key = { it.id }
                ) { app ->
                    AppListItem(
                        app = app,
                        onClick = { onAppClick(app.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun RuStoreHeader(
    onLogoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(RuStoreBlue)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .padding(top = 28.dp, bottom = 14.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onLogoClick() }
        ) {
            SubcomposeAsyncImage(
                model = "https://static.rustore.ru/imgproxy/WK9tZabN8zBri0KxEerdjyFQkgbEhrAkoIZL6QD7d80/rs:fit:1126:1125/g:so/dpr:2/plain/https://static.rustore.ru/rustore-strapi/6/Logo_Ru_Store_8567418d08.png@webp",
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.3f))
                    )
                },
                error = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.3f))
                    )
                }
            )
            Text(
                text = "RuStore",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        IconButton(
            onClick = { },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Apps,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun AppListItem(
    app: AppDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconShape = RoundedCornerShape(12.dp)
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = app.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(iconShape),
            loading = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(iconShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            },
            error = {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(iconShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                )
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = app.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = app.description,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = app.category.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1
            )
        }
    }
}


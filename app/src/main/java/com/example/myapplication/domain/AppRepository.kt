package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getApps(): List<AppDetails>
    fun getAppById(id: String): AppDetails?
    suspend fun getAppDetails(id: String): AppDetails
    fun observeAppDetails(id: String): Flow<AppDetails>
    suspend fun toggleWishlist(id: String)
}


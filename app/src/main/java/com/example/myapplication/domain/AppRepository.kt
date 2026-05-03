package com.example.myapplication.domain

interface AppRepository {
    suspend fun getApps(): List<AppDetails>
    fun getAppById(id: String): AppDetails?
    suspend fun getAppDetails(id: String): AppDetails
}


package com.example.myapplication.domain

interface AppRepository {
    fun getApps(): List<AppDetails>
    fun getAppById(id: String): AppDetails?
}


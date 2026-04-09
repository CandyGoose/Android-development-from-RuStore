package com.example.myapplication.data.appdetails.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.Category

@Entity(tableName = "app_details")
data class AppDetailsEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlsJson: String,
    val description: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

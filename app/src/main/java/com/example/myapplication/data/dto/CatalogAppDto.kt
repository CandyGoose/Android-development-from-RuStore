package com.example.myapplication.data.dto

data class CatalogAppDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
    val developer: String? = null,
    val ageRating: Int? = null,
    val size: Float? = null,
    val screenshotUrlList: List<String>? = null
)


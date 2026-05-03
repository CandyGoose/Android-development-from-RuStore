package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.CatalogAppDto
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.Category

fun CatalogAppDto.toDomain(): AppDetails =
    AppDetails(
        id = id,
        name = name,
        developer = developer.orEmpty(),
        category = Category.fromServerCategory(category),
        ageRating = ageRating ?: 0,
        size = size ?: 0f,
        iconUrl = iconUrl,
        screenshotUrlList = screenshotUrlList.orEmpty(),
        description = description
    )


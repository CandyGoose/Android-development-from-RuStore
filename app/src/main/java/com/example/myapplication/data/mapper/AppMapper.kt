package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.AppDto
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.Category

fun AppDto.toDomain(): AppDetails =
    AppDetails(
        id = id,
        name = name,
        developer = developer,
        category = Category.valueOf(category),
        ageRating = ageRating,
        size = size,
        iconUrl = iconUrl,
        screenshotUrlList = screenshotUrlList,
        description = description
    )


package com.example.myapplication.data.remote

import com.example.myapplication.data.dto.CatalogAppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogAppDto>

    @GET("catalog/{id}")
    suspend fun getCatalogById(@Path("id") id: String): CatalogAppDto
}


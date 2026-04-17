package com.example.myapplication.data

import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.AppRepository

class HardcodedAppRepository : AppRepository {

    override fun getApps(): List<AppDetails> {
        return HardcodedApps.appList.map { it.toDomain() }
    }

    override fun getAppById(id: String): AppDetails? {
        return HardcodedApps.getById(id)?.toDomain()
    }
}


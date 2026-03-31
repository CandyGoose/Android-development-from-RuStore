package com.example.myapplication.data

import com.example.myapplication.data.appdetails.local.AppDetailsDao
import com.example.myapplication.data.appdetails.local.AppDetailsEntityMapper
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.remote.CatalogApi
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.AppRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppRepositoryImpl @Inject constructor(
    private val catalogApi: CatalogApi,
    private val appDetailsDao: AppDetailsDao,
    private val appDetailsEntityMapper: AppDetailsEntityMapper
) : AppRepository {

    @Volatile
    private var cachedApps: List<AppDetails> = emptyList()

    override suspend fun getApps(): List<AppDetails> {
        val catalogApps = catalogApi.getCatalog()
        val domainApps = catalogApps.map { it.toDomain() }
        cachedApps = domainApps
        return domainApps
    }

    override fun getAppById(id: String): AppDetails? {
        return cachedApps.firstOrNull { it.id == id } ?: HardcodedApps.getById(id)?.toDomain()
    }

    override suspend fun getAppDetails(id: String): AppDetails {
        val entity = withContext(Dispatchers.IO) {
            appDetailsDao.getAppDetails(id).first()
        }
        if (entity != null) {
            return appDetailsEntityMapper.toDomain(entity)
        }
        val dto = catalogApi.getCatalogById(id)
        val domain = dto.toDomain()
        withContext(Dispatchers.IO) {
            appDetailsDao.insertAppDetails(appDetailsEntityMapper.toEntity(domain))
        }
        return domain
    }

    override fun observeAppDetails(id: String): Flow<AppDetails> {
        return appDetailsDao.getAppDetails(id)
            .filterNotNull()
            .map { appDetailsEntityMapper.toDomain(it) }
    }

    override suspend fun toggleWishlist(id: String) {
        val currentEntity = appDetailsDao.getAppDetails(id).first()
        currentEntity?.let {
            appDetailsDao.updateWishlistStatus(id, !it.isInWishlist)
        }
    }
}

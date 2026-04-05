package com.example.myapplication.data

import com.example.myapplication.data.appdetails.local.AppDetailsDao
import com.example.myapplication.data.appdetails.local.AppDetailsEntity
import com.example.myapplication.data.appdetails.local.AppDetailsEntityMapper
import com.example.myapplication.data.dto.CatalogAppDto
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.remote.CatalogApi
import com.example.myapplication.domain.AppDetails
import com.example.myapplication.domain.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {

    private val catalogApi: CatalogApi = mockk()
    private val appDetailsDao: AppDetailsDao = mockk(relaxed = true)
    private val entityMapper: AppDetailsEntityMapper = mockk()

    private lateinit var repository: AppRepositoryImpl

    @Before
    fun setUp() {
        repository = AppRepositoryImpl(catalogApi, appDetailsDao, entityMapper)
    }

    @Test
    fun `getApps loads catalog maps to domain and updates cache`() = runTest {
        val dto = sampleCatalogDto()
        coEvery { catalogApi.getCatalog() } returns listOf(dto)

        val result = repository.getApps()

        assertEquals(listOf(dto.toDomain()), result)
        assertEquals(dto.toDomain(), repository.getAppById(dto.id))
    }

    @Test
    fun `getAppById returns null when id is unknown`() {
        assertNull(repository.getAppById("unknown-id"))
    }

    @Test
    fun `getAppById returns hardcoded app when cache is empty`() {
        val domain = repository.getAppById("app-2")

        assertEquals("ВКонтакте", domain?.name)
        assertEquals(Category.SOCIAL, domain?.category)
    }

    @Test
    fun `getAppDetails returns domain from database when entity exists`() = runTest {
        val id = "db-app"
        val entity = sampleEntity(id)
        val domain = sampleDomain(id)
        coEvery { appDetailsDao.getAppDetails(id) } returns flowOf(entity)
        every { entityMapper.toDomain(entity) } returns domain

        val result = repository.getAppDetails(id)

        assertEquals(domain, result)
        coVerify(exactly = 0) { catalogApi.getCatalogById(any()) }
    }

    @Test
    fun `getAppDetails fetches from api inserts entity when db is empty`() = runTest {
        val id = "remote-app"
        val dto = sampleCatalogDto().copy(id = id)
        val domain = dto.toDomain()
        val entity = sampleEntity(id)
        coEvery { appDetailsDao.getAppDetails(id) } returns flowOf(null)
        coEvery { catalogApi.getCatalogById(id) } returns dto
        every { entityMapper.toEntity(domain) } returns entity

        val result = repository.getAppDetails(id)

        assertEquals(domain, result)
        coVerify { appDetailsDao.insertAppDetails(entity) }
    }

    @Test
    fun `toggleWishlist flips flag when entity is present`() = runTest {
        val id = "wish"
        val entity = sampleEntity(id).copy(isInWishlist = false)
        coEvery { appDetailsDao.getAppDetails(id) } returns flowOf(entity)

        repository.toggleWishlist(id)

        coVerify { appDetailsDao.updateWishlistStatus(id, true) }
    }

    @Test
    fun `observeAppDetails maps non null entities to domain`() = runTest {
        val id = "obs"
        val entity = sampleEntity(id)
        val domain = sampleDomain(id)
        coEvery { appDetailsDao.getAppDetails(id) } returns flowOf(entity)
        every { entityMapper.toDomain(entity) } returns domain

        val emitted = repository.observeAppDetails(id).first()

        assertEquals(domain, emitted)
        verify { entityMapper.toDomain(entity) }
    }

    private fun sampleCatalogDto() = CatalogAppDto(
        id = "c1",
        name = "Catalog App",
        description = "Desc",
        category = "Игры",
        iconUrl = "https://icon",
        developer = "Dev",
        ageRating = 12,
        size = 42f,
        screenshotUrlList = listOf("s1")
    )

    private fun sampleDomain(id: String) = AppDetails(
        id = id,
        name = "N",
        developer = "D",
        category = Category.GAME,
        ageRating = 0,
        size = 1f,
        iconUrl = "i",
        screenshotUrlList = emptyList(),
        description = "x"
    )

    private fun sampleEntity(id: String) = AppDetailsEntity(
        id = id,
        name = "N",
        developer = "D",
        category = Category.GAME,
        ageRating = 0,
        size = 1f,
        iconUrl = "i",
        screenshotUrlsJson = "[]",
        description = "x",
        isInWishlist = false
    )
}

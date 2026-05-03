package com.example.myapplication.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class DomainUseCasesTest {

    private val repository: AppRepository = mockk(relaxed = true)

    private lateinit var getAppsUseCase: GetAppsUseCase
    private lateinit var getAppByIdUseCase: GetAppByIdUseCase
    private lateinit var getAppDetailsUseCase: GetAppDetailsUseCase
    private lateinit var observeAppDetailsUseCase: ObserveAppDetailsUseCase
    private lateinit var toggleWishlistUseCase: ToggleWishlistUseCase

    private val sampleApp = AppDetails(
        id = "1",
        name = "App",
        developer = "D",
        category = Category.APP,
        ageRating = 0,
        size = 1f,
        iconUrl = "i",
        screenshotUrlList = emptyList(),
        description = "x"
    )

    @Before
    fun setUp() {
        getAppsUseCase = GetAppsUseCase(repository)
        getAppByIdUseCase = GetAppByIdUseCase(repository)
        getAppDetailsUseCase = GetAppDetailsUseCase(repository)
        observeAppDetailsUseCase = ObserveAppDetailsUseCase(repository)
        toggleWishlistUseCase = ToggleWishlistUseCase(repository)
    }

    @Test
    fun `GetAppsUseCase delegates to repository`() = runTest {
        val list = listOf(sampleApp)
        coEvery { repository.getApps() } returns list

        val result = getAppsUseCase()

        assertEquals(list, result)
        coVerify(exactly = 1) { repository.getApps() }
    }

    @Test
    fun `GetAppByIdUseCase returns repository value`() {
        every { repository.getAppById("id") } returns sampleApp

        assertSame(sampleApp, getAppByIdUseCase("id"))
        verify { repository.getAppById("id") }
    }

    @Test
    fun `GetAppByIdUseCase propagates null`() {
        every { repository.getAppById("missing") } returns null

        assertNull(getAppByIdUseCase("missing"))
    }

    @Test
    fun `GetAppDetailsUseCase delegates with id`() = runTest {
        coEvery { repository.getAppDetails("abc") } returns sampleApp

        val result = getAppDetailsUseCase("abc")

        assertSame(sampleApp, result)
        coVerify { repository.getAppDetails("abc") }
    }

    @Test
    fun `ObserveAppDetailsUseCase returns flow from repository`() {
        val flow = flowOf(sampleApp)
        every { repository.observeAppDetails("z") } returns flow

        val result = observeAppDetailsUseCase("z")

        assertSame(flow, result)
        verify { repository.observeAppDetails("z") }
    }

    @Test
    fun `ToggleWishlistUseCase delegates to repository`() = runTest {
        toggleWishlistUseCase("app-id")

        coVerify { repository.toggleWishlist("app-id") }
    }
}

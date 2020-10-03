package com.buffup.app

import com.buffup.app.core.Result
import com.buffup.app.core.api.ApiService
import com.buffup.app.core.api.response.VideoResponse
import com.buffup.app.core.di.createCoreModules
import com.buffup.app.core.usecase.FetchVideosUseCase
import com.buffup.app.feature.di.createFeatureModule
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@FlowPreview
@ExperimentalCoroutinesApi
class ApiTest : KoinTest {
    private val mockWebServer = MockWebServer()

    private lateinit var apiService: ApiService

    private val useCase by inject<FetchVideosUseCase>()

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().also {
                it.connectTimeout(30, TimeUnit.SECONDS)
                it.readTimeout(30, TimeUnit.SECONDS).build()
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(ApiService::class.java)

        startKoin {
            modules(createCoreModules(BuildConfig.BASE_URL) + createFeatureModule())
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        stopKoin()
    }

    @Test
    fun testApiResponse() = runBlocking {
        val response = apiService.getVideos(1)
        assert(response is VideoResponse)
    }

    @Test
    fun testUseCase() = runBlocking {
        val result = useCase.invoke(3)
        assert(result is Result.Success)
    }

    @Test
    fun failUseCase() = runBlocking {
        val result = useCase.invoke(9282)
        assert(result is Result.Error)
    }

    @Test
    fun testParams() = runBlocking {
        val result = apiService.getVideos(1)
        assert(result.videos.answer.isNotEmpty())
    }
}
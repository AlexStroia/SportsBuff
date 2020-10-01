package com.buffup.app.core.di

import com.buffup.app.core.repository.VideoRepository
import com.buffup.app.core.repository.remotesource.VideoRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
internal fun createRepositoryModule() = module {
    single { VideoRepository(videoRemoteSource = get()) }
    factory { VideoRemoteSource(apiService = get()) }
}
package com.buffup.app.core.di

import com.buffup.app.core.repository.VideoRepository
import com.buffup.app.core.repository.localsource.VideoLocalSource
import com.buffup.app.core.repository.remotesource.VideoRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal fun createRepositoryModule() = module {
    single { VideoRepository(videoRemoteSource = get(), localSource = get()) }
    factory { VideoRemoteSource(apiService = get()) }
    factory { VideoLocalSource() }
}
package com.buffup.app.core.di

import com.buffup.app.core.repository.VideoRepository
import com.buffup.app.core.repository.remote.VideoRemoteSource
import org.koin.dsl.module

internal fun createRepositoryModule() = module {
    single { VideoRepository(videoRemoteSource = get()) }
    factory { VideoRemoteSource() }
}
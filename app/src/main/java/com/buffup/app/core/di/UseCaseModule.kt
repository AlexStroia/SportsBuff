package com.buffup.app.core.di

import com.buffup.app.core.usecase.FetchVideosUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal fun createUseCaseModule() = module {
    factory { FetchVideosUseCase(videoRepository= get()) }
}
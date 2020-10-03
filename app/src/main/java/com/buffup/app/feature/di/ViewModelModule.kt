package com.buffup.app.feature.di

import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(FlowPreview::class)
@ExperimentalCoroutinesApi
fun viewModelModule() = module {
    viewModel { VideoFragmentViewModel(fetchVideosUseCase = get()) }
}
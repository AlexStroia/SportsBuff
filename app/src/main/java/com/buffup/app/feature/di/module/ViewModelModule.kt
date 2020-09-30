package com.buffup.app.feature.di.module

import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel { VideoFragmentViewModel(videoRepository = get()) }
}
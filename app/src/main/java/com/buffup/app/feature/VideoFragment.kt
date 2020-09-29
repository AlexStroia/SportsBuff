package com.buffup.app.feature

import com.buffup.app.R
import com.buffup.app.VideoFragmentBinding
import com.buffup.app.feature.viewmodel.VideoFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : SportsBuffFragment<VideoFragmentBinding, VideoFragmentViewModel>(R.layout.video_fragment) {
    override val viewModel by viewModel<VideoFragmentViewModel>()

}
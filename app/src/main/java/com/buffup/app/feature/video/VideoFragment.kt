package com.buffup.app.feature.video

import android.os.Bundle
import android.view.View
import com.buffup.app.R
import com.buffup.app.databinding.VideoFragmentBinding
import com.buffup.app.feature.SportsBuffFragment
import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment :
    SportsBuffFragment<VideoFragmentBinding, VideoFragmentViewModel>(R.layout.video_fragment) {
    @FlowPreview
    override val viewModel by viewModel<VideoFragmentViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            video.apply {
                setVideoPath("https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4")
                start()
            }
        }
    }
}
package com.buffup.app.feature.video

import android.os.Bundle
import android.view.View
import com.buffup.app.R
import com.buffup.app.databinding.VideoFragmentBinding
import com.buffup.app.feature.SportsBuffFragment
import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : SportsBuffFragment<VideoFragmentBinding, VideoFragmentViewModel>(R.layout.video_fragment) {
    override val viewModel by viewModel<VideoFragmentViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
package com.buffup.app.feature.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.buffup.app.R
import com.buffup.app.VideoFragmentBinding
import com.buffup.app.feature.SportsBuffFragment
import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : Fragment() {
    val viewModel by viewModel<VideoFragmentViewModel>()

    private lateinit var binding: VideoFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<VideoFragmentBinding>(inflater, R.layout.video_fragment, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
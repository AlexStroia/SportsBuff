package com.buffup.app.feature.video

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.buffup.app.R
import com.buffup.app.core.SportsBuffError
import com.buffup.app.databinding.VideoFragmentBinding
import com.buffup.app.feature.SportsBuffFragment
import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import com.buffup.app.shared.Event
import com.buffup.sdk.model.BuffUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
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

        with(viewModel) {
            event.observe(viewLifecycleOwner, Observer {
                when (val action = it.consume()) {
                    is VideoFragmentViewModel.Action.Error -> handleError(action.error)
                    is VideoFragmentViewModel.Action.Success -> {
                        with(binding) {
                            val answers = action.response.videos.answer.map {
                                BuffUiModel.Answer(
                                    id = it.id,
                                    text = it.title
                                )
                            }
                            val question = BuffUiModel.Question(
                                action.response.videos.question.id,
                                action.response.videos.question.title
                            )
                            val author = BuffUiModel.Author(
                                action.response.videos.author.firstName,
                                action.response.videos.author.lastName,
                                action.response.videos.author.image
                            )
                            val buffs = arrayListOf<BuffUiModel>()
                            buffs.addAll(answers)
                            buffs.add(question)
                            buffs.add(author)
                            buffView.apply {
                                setTime(action.response.videos.timeToShow.toLong())
                                setData(buffs)
                            }
                        }
                    }
                }
            })
        }
    }
}
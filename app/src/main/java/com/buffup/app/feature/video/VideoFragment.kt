package com.buffup.app.feature.video

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.buffup.app.R
import com.buffup.app.databinding.VideoFragmentBinding
import com.buffup.app.feature.SportsBuffFragment
import com.buffup.app.feature.video.viewmodel.VideoFragmentViewModel
import com.buffup.sdk.model.AnswerUiModel
import com.buffup.sdk.model.AuthorUiModel
import com.buffup.sdk.model.QuestionUiModel
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
                                AnswerUiModel(
                                    id = it.id,
                                    text = it.title,
                                    shouldAnimateOverlay = false,
                                    image = it.images.content0.url
                                )
                            }
                            val question = QuestionUiModel(
                                id = action.response.videos.question.id,
                                text = action.response.videos.question.title
                            )
                            val author = AuthorUiModel(
                                firstName = action.response.videos.author.firstName,
                                lastName = action.response.videos.author.lastName,
                                image = action.response.videos.author.image
                            )
                            buffView.apply {
                                setTime(action.response.videos.timeToShow.toLong())
                                setData(answers, question, author)
                            }
                        }
                    }
                }
            })
        }
    }
}
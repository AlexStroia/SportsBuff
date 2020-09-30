package com.buffup.app.feature.video.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.buffup.app.core.Result
import com.buffup.app.core.SportsBuffError
import com.buffup.app.core.asError
import com.buffup.app.core.repository.VideoRepository
import com.buffup.app.core.usecase.FetchVideosUseCase
import com.buffup.app.shared.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@FlowPreview
class VideoFragmentViewModel(
    private val videoRepository: VideoRepository,
    private val fetchVideosUseCase: FetchVideosUseCase
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val questions = videoRepository.getVideo().asLiveData()

    private val _event = MutableLiveData<Event<Action>>()
    val event: LiveData<Event<Action>> get() = _event

    private lateinit var timer: CountDownTimer

    init {
        viewModelScope.launch {
            handleFetchVideoUseCase(1)
        }

        var currentId = 0
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                currentId++
                viewModelScope.launch {
                    handleFetchVideoUseCase(currentId)
                }

                if (currentId < 5) {
                    timer.cancel()
                    timer.start()
                } else {
                    timer.cancel()
                }
            }
        }.start()
    }

    private suspend fun handleFetchVideoUseCase(id: Int) {
        _event.value = Event(Action.Loading(true))
        when (val result = fetchVideosUseCase(id)) {
            is Result.Success -> _event.value = Event(Action.Loading(false))
            is Result.Error -> {
                _event.value = Event(Action.Loading(false))
                _event.value = Event(Action.Error(result.exception.asError()))
            }
        }

    }


    sealed class Action {
        data class Error(val error: SportsBuffError) : Action()
        data class Loading(val isLoading: Boolean) : Action()
    }
}
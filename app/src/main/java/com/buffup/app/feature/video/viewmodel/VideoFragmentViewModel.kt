package com.buffup.app.feature.video.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.buffup.app.core.Result
import com.buffup.app.core.SportsBuffError
import com.buffup.app.core.api.response.VideoResponse
import com.buffup.app.core.asError
import com.buffup.app.core.usecase.FetchVideosUseCase
import com.buffup.app.shared.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


private const val MILLISECONDS_REQUEST_DELAY_TIME = 30000L
private const val MILLISECONDS_COUNTDOWN_INTERVAL = 1000L

@ExperimentalCoroutinesApi
@FlowPreview
class VideoFragmentViewModel(
    private val fetchVideosUseCase: FetchVideosUseCase
) : ViewModel() {

    private val _event = MutableLiveData<Event<Action>>()
    val event: LiveData<Event<Action>> get() = _event

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private lateinit var timer: CountDownTimer

    init {
        var currentId = 0
        timer =
            object : CountDownTimer(MILLISECONDS_REQUEST_DELAY_TIME, MILLISECONDS_COUNTDOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
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
        _loading.value = true
        when (val result = fetchVideosUseCase(id)) {
            is Result.Success -> {
                _loading.value = false
                _event.value = Event(Action.Success(result.value))
            }
            is Result.Error -> {
                _loading.value = false
                _event.value = Event(Action.Error(result.exception.asError()))
            }
        }
    }


    sealed class Action {
        data class Error(val error: SportsBuffError) : Action()
        data class Success(val response: VideoResponse) : Action()
    }
}
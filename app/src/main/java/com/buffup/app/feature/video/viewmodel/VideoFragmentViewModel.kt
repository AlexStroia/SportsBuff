package com.buffup.app.feature.video.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buffup.app.core.repository.VideoRepository

class VideoFragmentViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    val text = MutableLiveData<String>("PLM")

}
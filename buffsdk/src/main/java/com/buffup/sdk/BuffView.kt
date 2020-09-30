package com.buffup.sdk

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.buffup.sdk.adapter.BuffAdapter
import com.buffup.sdk.adapter.OnAnswerSelected
import com.buffup.sdk.adapter.OnCloseSelected
import com.buffup.sdk.model.BuffUiModel

class BuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private var binding: BuffViewBinding = BuffViewBinding.inflate(LayoutInflater.from(context))

    private val _content = MutableLiveData<BuffUiModel>()

    private val adapter by lazy {
        BuffAdapter(object : OnAnswerSelected {
            override fun invoke(uiModel: BuffUiModel) {
            }
        }, object : OnCloseSelected {
            override fun invoke() {
            }
        })
    }

    init {
        addView(binding.root)
        binding.plm.text = "Aloha"
        with(binding) {
            rvQuestions.adapter = adapter
        }
    }

    fun setData(uiModel: BuffUiModel) {
        adapter.submitList(listOf(uiModel))
    }
}
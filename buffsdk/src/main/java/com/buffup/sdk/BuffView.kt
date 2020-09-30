package com.buffup.sdk

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.buffup.sdk.adapter.BuffAdapter
import com.buffup.sdk.adapter.OnAnswerSelected
import com.buffup.sdk.adapter.OnCloseSelected
import com.buffup.sdk.model.BuffUiModel

class BuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val adapter = BuffAdapter(object : OnAnswerSelected {
            override fun invoke(uiModel: BuffUiModel) {
                TODO("Not yet implemented")
            }
        }, object : OnCloseSelected {
            override fun invoke() {
                TODO("Not yet implemented")
            }
        })
    }
}
package com.buffup.sdk.utils

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.buffup.sdk.R

private const val LEVEL_INCREMENT = 400
private const val MAX_LEVEL = 10000

@BindingAdapter("animateOverlay")
fun LinearLayout.animateOverlayColor(shouldAnimateOverlay: Boolean) {
    if (shouldAnimateOverlay) {
        this.setBackgroundResource(R.drawable.bg_selected_answer)
    }
}
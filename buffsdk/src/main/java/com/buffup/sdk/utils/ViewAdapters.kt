package com.buffup.sdk.utils
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.buffup.sdk.R

@BindingAdapter("animateOverlay")
fun LinearLayout.animateOverlayColor(shouldAnimateOverlay: Boolean) {
    if (shouldAnimateOverlay) {
        this.setBackgroundResource(R.drawable.bg_selected_answer)
    }
}
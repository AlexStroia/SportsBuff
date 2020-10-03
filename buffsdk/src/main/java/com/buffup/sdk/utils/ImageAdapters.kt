package com.buffup.sdk.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.buffup.sdk.R

@BindingAdapter("setImage")
fun ImageView.setImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("setImageAnswer")
fun ImageView.setImageAnswer(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.load(url) {
            crossfade(false)
                .error(R.drawable.ic_generic_answer)
            transformations(CircleCropTransformation())
        }
    }
}
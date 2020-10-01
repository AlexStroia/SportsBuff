package com.buffup.sdk.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImage")
fun ImageView.setImage(url: String) {
    Glide.with(this).load(url).into(this)
}
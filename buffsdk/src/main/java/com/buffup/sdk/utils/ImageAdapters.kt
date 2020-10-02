package com.buffup.sdk.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.buffup.sdk.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@SuppressLint("CheckResult")
private fun buildRequestOptionsForAnswer(): RequestOptions {
    val requestOptions = RequestOptions()
    requestOptions.error(R.drawable.ic_generic_answer)
    return requestOptions
}

@BindingAdapter("setImage")
fun ImageView.setImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load(url).into(this)
    }
}

@BindingAdapter("setImageAnswer")
fun ImageView.setImageAnswer(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .setDefaultRequestOptions(buildRequestOptionsForAnswer())
            .load(url).into(this)
    }
}
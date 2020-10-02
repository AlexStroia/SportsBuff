package com.buffup.sdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.buffup.sdk.BuffAnswerUiBinding
import com.buffup.sdk.model.BuffUiModel
import com.buffup.sdk.shared.DataBoundListAdapter

typealias OnAnswerSelected = (uiModel: BuffUiModel.Answer) -> Unit

class BuffAdapter(
    private val onAnswerSelected: OnAnswerSelected,
) : DataBoundListAdapter<BuffUiModel.Answer, BuffAnswerUiBinding>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<BuffUiModel.Answer>() {
        override fun areItemsTheSame(
            oldItem: BuffUiModel.Answer,
            newItem: BuffUiModel.Answer
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: BuffUiModel.Answer,
            newItem: BuffUiModel.Answer
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): BuffAnswerUiBinding =
        BuffAnswerUiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun bind(binding: BuffAnswerUiBinding, item: BuffUiModel.Answer, position: Int) {
        with(binding) {
            uiModel = item
            root.setOnClickListener { onAnswerSelected(item) }
        }
    }
}
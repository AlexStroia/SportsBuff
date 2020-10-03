package com.buffup.sdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.buffup.sdk.BuffAnswerUiBinding
import com.buffup.sdk.model.AnswerUiModel
import com.buffup.sdk.shared.DataBoundListAdapter

typealias OnAnswerSelected = (uiModel: AnswerUiModel) -> Unit

class BuffAdapter(
    private val onAnswerSelected: OnAnswerSelected,
) : DataBoundListAdapter<AnswerUiModel, BuffAnswerUiBinding>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<AnswerUiModel>() {
        override fun areItemsTheSame(
            oldItem: AnswerUiModel,
            newItem: AnswerUiModel
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: AnswerUiModel,
            newItem: AnswerUiModel
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

    override fun bind(binding: BuffAnswerUiBinding, item: AnswerUiModel, position: Int) {
        with(binding) {
            uiModel = item
            root.setOnClickListener { onAnswerSelected(item) }
        }
    }
}
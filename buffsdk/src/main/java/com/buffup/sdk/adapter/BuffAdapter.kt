package com.buffup.sdk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.buffup.sdk.BuffAnswerUiBinding
import com.buffup.sdk.model.AnswerUiModel
import com.buffup.sdk.shared.DataBoundListAdapter
import com.buffup.sdk.shared.DataBoundViewHolder
import com.buffup.sdk.utils.setImage

typealias OnAnswerSelected = (uiModel: AnswerUiModel) -> Unit

class BuffAdapter(
    private val onAnswerSelected: OnAnswerSelected,
) : DataBoundListAdapter<AnswerUiModel, BuffAnswerUiBinding>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<AnswerUiModel>() {
        override fun areItemsTheSame(
            oldItem: AnswerUiModel,
            newItem: AnswerUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnswerUiModel,
            newItem: AnswerUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindingViewHolderCreated(holder: DataBoundViewHolder<BuffAnswerUiBinding>) {
        with(holder.binding) {
            if(holder.adapterPosition != -1) {
                this.image.setImage(getItem(holder.adapterPosition).image)
            }
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
            root.setOnClickListener {
                onAnswerSelected(item)
            }
        }
    }
}
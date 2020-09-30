package com.buffup.sdk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.buffup.sdk.BuffAnswerUiBinding
import com.buffup.sdk.BuffQuestionUiBinding
import com.buffup.sdk.BuffSenderUiBinding
import com.buffup.sdk.model.BuffUiModel
import com.buffup.sdk.shared.DataBoundListAdapter
import java.lang.IllegalArgumentException

typealias OnAnswerSelected = (uiModel: BuffUiModel) -> Unit

class BuffAdapter(onAnswerSelected: OnAnswerSelected) : DataBoundListAdapter<BuffUiModel, ViewDataBinding>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<BuffUiModel>() {
        override fun areItemsTheSame(oldItem: BuffUiModel, newItem: BuffUiModel): Boolean = when {
            oldItem is BuffUiModel.Answer && newItem is BuffUiModel.Answer -> oldItem.text == newItem.text
            oldItem is BuffUiModel.Question && newItem is BuffUiModel.Question -> oldItem.id == newItem.id
            oldItem is BuffUiModel.Author && newItem is BuffUiModel.Author -> oldItem.id == newItem.id
            else -> false
        }

        override fun areContentsTheSame(oldItem: BuffUiModel, newItem: BuffUiModel): Boolean =
            when {
                oldItem is BuffUiModel.Answer && newItem is BuffUiModel.Answer -> oldItem == newItem
                oldItem is BuffUiModel.Question && newItem is BuffUiModel.Question -> oldItem == newItem
                oldItem is BuffUiModel.Author && newItem is BuffUiModel.Author -> oldItem == newItem
                else -> false
            }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding =
        when (viewType) {
            ITEM_QUESTION -> BuffQuestionUiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ITEM_ANSWER -> BuffAnswerUiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ITEM_SENDER -> BuffSenderUiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> throw IllegalArgumentException("Invalid binding creation")
        }

    override fun bind(binding: ViewDataBinding, item: BuffUiModel, position: Int) = when (item) {
        is BuffUiModel.Author -> (binding as? BuffSenderUiBinding)?.uiModel = item
        is BuffUiModel.Question -> (binding as? BuffQuestionUiBinding)?.uiModel = item
        is BuffUiModel.Answer -> (binding as? BuffAnswerUiBinding)?.uiModel = item
    }

    companion object {
        private const val ITEM_QUESTION = 3
        private const val ITEM_ANSWER = 2
        private const val ITEM_SENDER = 1
    }

}
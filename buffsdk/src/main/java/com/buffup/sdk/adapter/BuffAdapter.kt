package com.buffup.sdk.adapter

import android.view.LayoutInflater
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
typealias OnCloseSelected = () -> Unit

class BuffAdapter(
    private val onAnswerSelected: OnAnswerSelected,
    private val onCloseSelected: OnCloseSelected
) : DataBoundListAdapter<BuffUiModel, ViewDataBinding>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<BuffUiModel>() {
        override fun areItemsTheSame(oldItem: BuffUiModel, newItem: BuffUiModel): Boolean = when {
            oldItem is BuffUiModel.Answer && newItem is BuffUiModel.Answer -> oldItem.text == newItem.text
            oldItem is BuffUiModel.Question && newItem is BuffUiModel.Question -> oldItem.id == newItem.id
            oldItem is BuffUiModel.Author && newItem is BuffUiModel.Author -> oldItem.firstName == newItem.firstName
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

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is BuffUiModel.Author -> ITEM_SENDER
        is BuffUiModel.Question -> ITEM_QUESTION
        is BuffUiModel.Answer -> ITEM_ANSWER
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

    override fun bind(binding: ViewDataBinding, item: BuffUiModel, position: Int) {
        when (item) {
            is BuffUiModel.Author -> (binding as? BuffSenderUiBinding)?.let {
                with(it) {
                    uiModel = item
                    root.setOnClickListener { onAnswerSelected(item) }
                    buffClose.setOnClickListener { onCloseSelected() }
                }
            }
            is BuffUiModel.Question -> (binding as? BuffQuestionUiBinding)?.let {
                with(it) {
                    uiModel = item
                    root.setOnClickListener { onAnswerSelected(item) }
                }
            }
            is BuffUiModel.Answer -> (binding as? BuffAnswerUiBinding)?.let {
                with(it) {
                    uiModel = item
                    root.setOnClickListener { onAnswerSelected(item) }
                }
            }
        }
    }

    companion object {
        private const val ITEM_QUESTION = 2
        private const val ITEM_ANSWER = 1
        private const val ITEM_SENDER = 0
    }

}
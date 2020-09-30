package com.buffup.sdk.shared

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <VB> The type of the ViewDataBinding
</VB></T> */
abstract class DataBoundListAdapter<T, VB : ViewDataBinding>(diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, DataBoundViewHolder<VB>>(diffUtil) {

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<VB> {
        val holder = DataBoundViewHolder(createBinding(parent, viewType))
        onBindingViewHolderCreated(holder)
        return holder
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): VB

    protected open fun onBindingViewHolderCreated(holder: DataBoundViewHolder<VB>) = Unit

    @CallSuper
    override fun onBindViewHolder(holder: DataBoundViewHolder<VB>, position: Int) {
        bind(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: VB, item: T, position: Int)
}

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
 */
open class DataBoundViewHolder<out VB : ViewDataBinding> constructor(val binding: VB) : RecyclerView.ViewHolder(binding.root)
package com.foxek.simpletimer.presentation.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<M, VH : BaseAdapter.BaseViewHolder<M>> : RecyclerView.Adapter<VH>() {

    private val items = ArrayList<M>()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: VH) {
        holder.unbind()
    }

    override fun getItemCount(): Int = items.size

    open fun getDiffCallback(oldItems: List<M>, newItems: List<M>): DiffUtil.Callback? = null

    fun getItems(): List<M> = items

    fun setItems(newItems: List<M>) {
        getDiffCallback(items, newItems)?.let {
            val diffResult = DiffUtil.calculateDiff(it)
            replaceItems(newItems)
            diffResult.dispatchUpdatesTo(this)
        } ?: run {
            replaceItems(newItems)
        }
    }

    private fun replaceItems(newItems: List<M>) {
        items.clear()
        items.addAll(newItems)
    }

    abstract class BaseViewHolder<M>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(model: M)

        fun unbind() {

        }
    }
}
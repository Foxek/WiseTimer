package com.foxek.simpletimer.presentation.base

import androidx.recyclerview.widget.DiffUtil


abstract class BaseDiffCallback<T>(
    private var oldList: List<T>?,
    private var newList: List<T>?
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }
}

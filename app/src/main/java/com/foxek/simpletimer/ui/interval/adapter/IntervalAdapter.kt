package com.foxek.simpletimer.ui.interval.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.foxek.simpletimer.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foxek.simpletimer.data.model.Interval

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.formatIntervalData
import kotlinx.android.synthetic.main.interval_item.view.*

class IntervalAdapter : ListAdapter<Interval, IntervalAdapter.ViewHolder>(IntervalDiffCallback()) {

    private var mCallback: Callback? = null

    interface Callback {
        fun onListItemClick(item: Interval)
    }

    internal fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (getItem(position).name == EMPTY) {
            holder.itemView.titleGroup.visibility = View.GONE
        } else {
            holder.itemView.titleGroup.visibility = View.VISIBLE
            holder.itemView.intervalTitle.text = getItem(position).name
        }

        holder.itemView.etWorkTime.text = formatIntervalData(getItem(position).workTime)
        holder.itemView.etRestTime.text = formatIntervalData(getItem(position).restTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.interval_item, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v) {

        init {
            itemView.item.setOnClickListener { mCallback?.onListItemClick(getItem(adapterPosition)) }
        }
    }
}
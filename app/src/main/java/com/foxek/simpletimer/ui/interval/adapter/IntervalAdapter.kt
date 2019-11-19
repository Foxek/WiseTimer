package com.foxek.simpletimer.ui.interval.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.foxek.simpletimer.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foxek.simpletimer.data.model.Interval

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.ONLY_REST_TYPE
import com.foxek.simpletimer.utils.Constants.ONLY_WORK_TYPE
import com.foxek.simpletimer.utils.Constants.WORK_AND_REST_TYPE
import com.foxek.simpletimer.utils.formatIntervalData
import kotlinx.android.synthetic.main.multi_interval_item.view.*
import kotlinx.android.synthetic.main.multi_interval_item.view.item
import kotlinx.android.synthetic.main.multi_interval_item.view.titleGroup
import kotlinx.android.synthetic.main.single_interval_item.view.*

class IntervalAdapter : ListAdapter<Interval, IntervalAdapter.ViewHolder>(IntervalDiffCallback()) {

    private var mCallback: Callback? = null

    interface Callback {
        fun onListItemClick(item: Interval)
    }

    internal fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when(getItem(position).type){
            WORK_AND_REST_TYPE -> {
                holder.itemView.etWorkTime.text = formatIntervalData(getItem(position).workTime)
                holder.itemView.etRestTime.text = formatIntervalData(getItem(position).restTime)
                holder.itemView.intervalTitle.text = getItem(position).name
            }
            ONLY_WORK_TYPE -> {
                holder.itemView.hint.text =  holder.itemView.context.getString(R.string.timer_work_time)
                holder.itemView.time.text = formatIntervalData(getItem(position).workTime)
                holder.itemView.title.text = getItem(position).name
            }
            ONLY_REST_TYPE -> {
                holder.itemView.hint.text =  holder.itemView.context.getString(R.string.timer_rest_time)
                holder.itemView.time.text = formatIntervalData(getItem(position).restTime)
                holder.itemView.title.text = getItem(position).name
            }
        }

        if (getItem(position).name == EMPTY) {
            holder.itemView.titleGroup.visibility = View.GONE
        } else {
            holder.itemView.titleGroup.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = if (viewType == WORK_AND_REST_TYPE){
            inflater.inflate(R.layout.multi_interval_item, parent, false)
        }else{
            inflater.inflate(R.layout.single_interval_item, parent, false)
        }

        return ViewHolder(view)
    }


    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v) {

        init {
            itemView.item.setOnClickListener { mCallback?.onListItemClick(getItem(adapterPosition)) }
        }
    }
}
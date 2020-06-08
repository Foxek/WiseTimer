package com.foxek.simpletimer.ui.interval.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.utils.formatIntervalData
import com.foxek.simpletimer.utils.formatIntervalNumber
import kotlinx.android.synthetic.main.multi_interval_item.view.*

class IntervalAdapter : ListAdapter<Interval, IntervalAdapter.ViewHolder> (IntervalDiffCallback()) {

    private var mCallback: Callback? = null

    init {
        setHasStableIds(true)
    }

    interface Callback {
        fun onListItemClick(item: Interval)
    }

    internal fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        // small fix
        holder.itemView.post { notifyItemRangeChanged(holder.layoutPosition + 1,itemCount)}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.item_interval_work_time.text = formatIntervalData(getItem(position).work)

        if (getItem(position).type == WITH_REST_TYPE) {
            holder.itemView.item_interval_rest_time.text = formatIntervalData(getItem(position).rest)
        }else{
            holder.itemView.item_interval_rest_hint.text = holder.itemView.resources.getString(R.string.timer_without_rest)
        }

        if (getItem(position).name == EMPTY) {
            holder.itemView.item_interval_title.text = formatIntervalNumber(position+1)
        }else{
            holder.itemView.item_interval_title.text = getItem(position).name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.multi_interval_item, parent, false)
        return ViewHolder(view)
    }


    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v) {

        init {
            itemView.item.setOnClickListener { mCallback?.onListItemClick(getItem(adapterPosition)) }
        }
    }
}
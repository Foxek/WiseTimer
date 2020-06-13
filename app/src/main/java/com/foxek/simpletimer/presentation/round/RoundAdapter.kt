package com.foxek.simpletimer.presentation.round

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.common.utils.Constants.EMPTY
import com.foxek.simpletimer.common.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.common.utils.formatIntervalData
import com.foxek.simpletimer.common.utils.formatIntervalNumber
import com.foxek.simpletimer.presentation.base.BaseAdapter
import com.foxek.simpletimer.presentation.base.BaseDiffCallback
import kotlinx.android.synthetic.main.item_interval.view.*

class RoundAdapter : BaseAdapter<Round, RoundAdapter.ViewHolder>() {

    var clickListener: ((round: Round) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        // small fix
        holder.itemView.post {
            notifyItemRangeChanged(holder.layoutPosition + 1, itemCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return with(parent) {
            LayoutInflater.from(context).inflate(R.layout.item_interval, this, false)
        }.run(::ViewHolder)
    }

    override fun getDiffCallback(oldItems: List<Round>, newItems: List<Round>): DiffUtil.Callback? {
        return object : BaseDiffCallback<Round>(oldItems, newItems) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem.workInterval == newItem.workInterval
                    && oldItem.restInterval == newItem.restInterval
                    && oldItem.name == newItem.name
                    && oldItem.type == newItem.type
            }
        }
    }

    inner class ViewHolder constructor(view: View) : BaseViewHolder<Round>(view) {

        override fun bind(model: Round) {
            itemView.apply {
                item_interval_work_time.text = formatIntervalData(model.workInterval)

                if (model.type == WITH_REST_TYPE) {
                    item_interval_rest_time.text = formatIntervalData(model.restInterval)
                } else {
                    item_interval_rest_hint.text = resources.getString(R.string.timer_without_rest)
                }

                item_interval_title.text = if (model.name == EMPTY) {
                    formatIntervalNumber(adapterPosition + 1)
                } else {
                    model.name
                }

                setOnClickListener { clickListener?.invoke(model) }
            }
        }
    }
}
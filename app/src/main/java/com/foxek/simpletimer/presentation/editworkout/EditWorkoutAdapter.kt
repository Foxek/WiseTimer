package com.foxek.simpletimer.presentation.editworkout

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.common.utils.formatIntervalData
import com.foxek.simpletimer.common.utils.formatIntervalNumber
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseAdapter
import com.foxek.simpletimer.presentation.base.BaseDiffCallback
import kotlinx.android.synthetic.main.item_round.view.*
import java.util.*

class EditWorkoutAdapter : BaseAdapter<Round, EditWorkoutAdapter.ViewHolder>(), RoundMoveListener {

    var onDragListener: ((viewHolder: RecyclerView.ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return with(parent) {
            LayoutInflater.from(context).inflate(R.layout.item_round, this, false)
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

    inner class ViewHolder(itemView: View) : BaseAdapter.BaseViewHolder<Round>(itemView) {
        override fun bind(model: Round) {
            itemView.apply {
                item_interval_work_time.text = formatIntervalData(model.workInterval)

                if (model.type == Constants.WITH_REST_TYPE) {
                    item_interval_rest_time.text = formatIntervalData(model.restInterval)
                    item_interval_rest_hint.text = resources.getString(R.string.timer_rest_time)
                } else {
                    item_interval_rest_time.text = ""
                    item_interval_rest_hint.text = resources.getString(R.string.timer_without_rest)
                }

                item_interval_title.text = if (model.name == Constants.EMPTY) {
                    formatIntervalNumber(model.positionInWorkout + 1)
                } else {
                    model.name
                }
                setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        onDragListener?.invoke(this@ViewHolder)
                        return@setOnTouchListener true
                    } else if (event.action == MotionEvent.ACTION_UP) {
                        performClick()
                        return@setOnTouchListener true
                    }
                    return@setOnTouchListener false
                }
            }
        }

    }

    override fun onRoundMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(getItems(), i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(getItems(), i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRoundMoved(fromPosition: Int, toPosition: Int) {
        applyNewRoundsOrder(getItems())
    }

    private fun applyNewRoundsOrder(rounds: List<Round>) {
        rounds.forEachIndexed { idx, value -> value.positionInWorkout = idx }
    }
}

interface RoundMoveListener {
    fun onRoundMove(fromPosition: Int, toPosition: Int)
    fun onRoundMoved(fromPosition: Int, toPosition: Int)
}
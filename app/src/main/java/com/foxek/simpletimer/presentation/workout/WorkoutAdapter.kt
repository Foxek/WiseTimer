package com.foxek.simpletimer.presentation.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.presentation.base.BaseAdapter
import com.foxek.simpletimer.presentation.base.BaseDiffCallback
import kotlinx.android.synthetic.main.item_workout.view.item_workout_description
import kotlinx.android.synthetic.main.item_workout.view.item_workout_name

class WorkoutAdapter : BaseAdapter<Workout, WorkoutAdapter.ViewHolder>() {

    var clickListener: ((workout: Workout) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return with(parent) {
            LayoutInflater.from(context).inflate(R.layout.item_workout, this, false)
        }.run(::ViewHolder)
    }

    override fun getDiffCallback(oldItems: List<Workout>, newItems: List<Workout>): DiffUtil.Callback? {
        return object : BaseDiffCallback<Workout>(oldItems, newItems) {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem.name == newItem.name
                    && oldItem.uid == newItem.uid
                    && oldItem.intervalCount == newItem.intervalCount
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem.name == newItem.name
                    && oldItem.uid == newItem.uid
                    && oldItem.intervalCount == newItem.intervalCount
            }
        }
    }

    inner class ViewHolder constructor(view: View) : BaseViewHolder<Workout>(view) {

        override fun bind(model: Workout) {
            itemView.apply {
                item_workout_name.text = model.name
                item_workout_description.text = resources.getString(
                    R.string.number_of_intervals_text,
                    model.intervalCount
                )
                setOnClickListener {
                    clickListener?.invoke(model)
                }
            }
        }
    }
}

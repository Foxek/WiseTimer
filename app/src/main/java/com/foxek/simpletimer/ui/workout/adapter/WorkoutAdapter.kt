package com.foxek.simpletimer.ui.workout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.foxek.simpletimer.R

import javax.inject.Inject
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foxek.simpletimer.data.model.Workout

import kotlinx.android.synthetic.main.workout_item.view.*

class WorkoutAdapter @Inject constructor() : ListAdapter<Workout, WorkoutAdapter.ViewHolder>(WorkoutDiffCallback()) {

    private var mCallback: Callback? = null

    interface Callback {
        fun onListItemClick(workout: Workout)
    }

    internal fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_workout_name.text = getItem(position).name
        holder.itemView.item_workout_description.text = holder.itemView.resources.getString(R.string.number_of_intervals_text,
                getItem(position).intervalCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.workout_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v) {

        init {
            itemView.workoutItem.setOnClickListener {
                mCallback?.onListItemClick(getItem(adapterPosition))
            }
        }
    }
}

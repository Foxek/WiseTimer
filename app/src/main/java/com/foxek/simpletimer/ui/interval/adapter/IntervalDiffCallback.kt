package com.foxek.simpletimer.ui.interval.adapter

import androidx.recyclerview.widget.DiffUtil
import com.foxek.simpletimer.data.model.Interval

class IntervalDiffCallback : DiffUtil.ItemCallback<Interval>() {

    override fun areItemsTheSame(oldInterval: Interval, newInterval: Interval): Boolean {
        return (oldInterval.id == newInterval.id)
    }

    override fun areContentsTheSame(oldInterval: Interval, newInterval: Interval): Boolean {
        return (oldInterval.work == newInterval.work) and
            (oldInterval.rest == newInterval.rest) and
            (oldInterval.name == newInterval.name) and
            (oldInterval.type == newInterval.type)
    }
}

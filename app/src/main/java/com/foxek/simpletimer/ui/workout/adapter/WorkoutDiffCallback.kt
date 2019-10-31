package com.foxek.simpletimer.ui.workout.adapter

import androidx.recyclerview.widget.DiffUtil
import com.foxek.simpletimer.data.model.Workout
import javax.inject.Inject

class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {

    override fun areItemsTheSame(oldWorkout: Workout, newWorkout: Workout): Boolean {
        return (oldWorkout.name == newWorkout.name) and
                (oldWorkout.intervalCount == newWorkout.intervalCount) and
                (oldWorkout.uid == newWorkout.uid)
    }

    override fun areContentsTheSame(oldWorkout: Workout, newWorkout: Workout): Boolean {
        return (oldWorkout.name == newWorkout.name) and
                (oldWorkout.intervalCount == newWorkout.intervalCount) and
                (oldWorkout.uid == newWorkout.uid)
    }
}
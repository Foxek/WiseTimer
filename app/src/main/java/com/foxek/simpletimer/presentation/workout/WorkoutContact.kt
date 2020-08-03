package com.foxek.simpletimer.presentation.workout

import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.presentation.base.BaseContract

interface WorkoutContact {

    interface View : BaseContract.View {

        fun startRoundFragment(workoutId: Int)

        fun renderWorkoutList(items: List<Workout>)

        fun showCreateDialog()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onSaveButtonClick(workoutName: String)

        fun onCreateButtonClick()
        
        fun onWorkoutItemClick(workout: Workout)
    }
}


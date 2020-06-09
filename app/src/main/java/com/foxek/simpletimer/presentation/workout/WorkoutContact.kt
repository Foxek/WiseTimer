package com.foxek.simpletimer.presentation.workout

import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.presentation.base.MvpPresenter
import com.foxek.simpletimer.presentation.base.MvpView

import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface WorkoutContact {

    interface View : MvpView {
        //TODO: передавать только id получать нейм в другом фрагменте
        fun startIntervalFragment(workoutId: Int)
        fun renderWorkoutList(items: List<Workout>)
        fun showCreateDialog()
    }

    interface Presenter : MvpPresenter<View> {
        fun onSaveButtonClick(workoutName: String)
        fun onCreateButtonClick()
        fun onWorkoutItemClick(workout: Workout)
    }
}


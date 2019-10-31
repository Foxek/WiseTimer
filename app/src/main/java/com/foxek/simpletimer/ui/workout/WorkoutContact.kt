package com.foxek.simpletimer.ui.workout

import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.ui.base.MvpPresenter
import com.foxek.simpletimer.ui.base.MvpView

import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface WorkoutContact {

    interface View : MvpView {

        fun startIntervalActivity(position: Int, name: String)

        fun setWorkoutList()

        fun renderWorkoutList(workoutList: List<Workout>)

        fun showCreateDialog()
    }

    interface Presenter : MvpPresenter<View> {

        fun saveButtonClicked(name: String)

        fun createButtonClicked()

        fun onListItemClicked(item: Workout)

    }

    interface Interactor {

        fun fetchWorkoutList(): Flowable<List<Workout>>

        fun createWorkout(name: String): Disposable
    }
}


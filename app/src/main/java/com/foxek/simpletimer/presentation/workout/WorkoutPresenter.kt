package com.foxek.simpletimer.presentation.workout

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter
import io.reactivex.rxkotlin.subscribeBy

import javax.inject.Inject

class WorkoutPresenter @Inject constructor(
    private val interactor: WorkoutInteractor
) : BasePresenter<WorkoutContact.View>(), WorkoutContact.Presenter {

    override fun viewIsReady() {
        fetchWorkoutList()
    }

    override fun onSaveButtonClick(workoutName: String) {
        interactor.createWorkout(workoutName)
            .observeOnMain()
            .subscribe()
            .disposeOnDestroy()
    }

    override fun onCreateButtonClick() {
        view?.showCreateDialog()
    }

    override fun onWorkoutItemClick(workout: Workout) {
        view?.startRoundFragment(workout.id)
    }

    private fun fetchWorkoutList() {
        interactor.observeWorkouts()
            .observeOnMain()
            .subscribeBy { view?.renderWorkoutList(it) }
            .disposeOnDestroy()
    }

}

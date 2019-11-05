package com.foxek.simpletimer.ui.workout

import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.ui.base.BasePresenter

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers

class WorkoutPresenter @Inject constructor(
        private val interactor: WorkoutContact.Interactor
) : BasePresenter<WorkoutContact.View>(), WorkoutContact.Presenter {

    override fun viewIsReady() {
        disposable.add(interactor.fetchWorkoutList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ workouts ->
                    view?.renderWorkoutList(workouts)
                }, {

                })
        )
    }

    override fun saveButtonClicked(name: String) {
        disposable.add(interactor.createWorkout(name))
    }

    override fun createButtonClicked() {
        view?.showCreateDialog()
    }

    override fun onListItemClicked(item: Workout) {
        view?.startIntervalFragment(item.uid, item.name)
    }
}

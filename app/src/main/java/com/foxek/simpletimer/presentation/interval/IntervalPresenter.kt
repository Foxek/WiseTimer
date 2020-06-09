package com.foxek.simpletimer.presentation.interval

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.domain.interval.IntervalInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter

import javax.inject.Inject

import io.reactivex.rxkotlin.subscribeBy

class IntervalPresenter @Inject constructor(
    private val intervalInteractor: IntervalInteractor,
    private val workoutInteractor: WorkoutInteractor
) : BasePresenter<IntervalContact.View>(), IntervalContact.Presenter {

    private var intervalId = NO_ID_INT

    override var workoutId: Int = NO_ID_INT

    override fun viewIsReady() {
        view?.setIntervalList()

        getCurrentWorkout()
        fetchIntervalList()
    }

    override fun editWorkoutButtonClicked() {
        view?.showWorkoutEditDialog()
    }

    override fun changeVolumeButtonClicked() {
        workoutInteractor.toggleWorkoutVolumeState(workoutId)
            .observeOnMain()
            .subscribeBy(
                onSuccess = { view?.setVolumeState(it) }
            )
            .disposeOnDestroy()
    }

    override fun addIntervalButtonClicked() {
        view?.showIntervalCreateDialog()
    }

    override fun saveIntervalButtonClicked(name: String, type: Int, workTime: Int, restTime: Int) {
        intervalInteractor.updateInterval(workoutId, intervalId,  name, type, workTime, restTime)
            .subscribe()
            .disposeOnDestroy()
    }

    override fun createIntervalButtonClicked(name: String, type: Int, workTime: Int, restTime: Int) {
        intervalInteractor.addInterval(Interval(name, workTime, restTime, workoutId, type, 0))
            .subscribe()
            .disposeOnDestroy()
    }

    override fun deleteIntervalButtonClicked() {
        intervalInteractor.deleteInterval(workoutId, intervalId)
            .subscribe()
            .disposeOnDestroy()
    }

    override fun saveWorkoutButtonClicked(name: String) {
        workoutInteractor.updateWorkoutName(workoutId, name)
            .subscribe()
            .disposeOnDestroy()

        view?.setWorkoutName(name)
    }

    override fun deleteWorkoutButtonClicked() {
        workoutInteractor.deleteWorkoutById(workoutId)
            .observeOnMain()
            .subscribeBy(
                onComplete = { view?.startWorkoutActivity() }
            )
            .disposeOnDestroy()
    }

    override fun startWorkoutButtonClicked() {
        view?.startTimerActivity()
    }

    override fun intervalItemClicked(item: Interval) {
        intervalId = item.id
        view?.showIntervalEditDialog(item)
    }

    private fun fetchIntervalList() {
        intervalInteractor.observeIntervals(workoutId)
            .observeOnMain()
            .subscribeBy(
                onNext = {
                    view?.renderIntervalList(it)
                }
            )
            .disposeOnDestroy()
    }

    private fun getCurrentWorkout() {
        workoutInteractor.getWorkoutById(workoutId)
            .observeOnMain()
            .subscribeBy(
                onSuccess = {
                    view?.setWorkoutName(it.name!!)
                    view?.setVolumeState(it.isVolume)
                }
            )
            .disposeOnDestroy()
    }
}
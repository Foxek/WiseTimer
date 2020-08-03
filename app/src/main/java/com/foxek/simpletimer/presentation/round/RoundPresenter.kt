package com.foxek.simpletimer.presentation.round

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter

import javax.inject.Inject

import io.reactivex.rxkotlin.subscribeBy

class RoundPresenter @Inject constructor(
    private val roundInteractor: RoundInteractor,
    private val workoutInteractor: WorkoutInteractor
) : BasePresenter<RoundContact.View>(), RoundContact.Presenter {

    private var intervalId = NO_ID_INT

    override var workoutId: Int = NO_ID_INT

    override fun onViewResumed() {
        getCurrentWorkout()
        fetchIntervalList()
    }

    override fun onEditWorkoutBtnClick() {
        view?.startEditWorkoutFragment(workoutId)
    }

    override fun onToggleSilentModeBtnClick() {
        workoutInteractor.toggleWorkoutVolumeState(workoutId)
            .observeOnMain()
            .subscribeBy(
                onSuccess = { view?.setSilentMode(it) }
            )
            .disposeOnPause()
    }

    override fun onAddRoundBtnClick() {
        view?.showRoundCreateDialog()
    }

    override fun onSaveRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int) {
        roundInteractor.updateRound(workoutId, intervalId,  name, type, workTime, restTime)
            .subscribe()
            .disposeOnPause()
    }

    override fun onCreateRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int) {
        roundInteractor.addRound(Round(name, type, workTime, restTime, workoutId, 0))
            .subscribe()
            .disposeOnPause()
    }

    override fun onDeleteRoundBtnClick() {
        roundInteractor.deleteRound(workoutId, intervalId)
            .subscribe()
            .disposeOnPause()
    }

    override fun onSaveWorkoutBtnClick(name: String) {
        workoutInteractor.updateWorkoutName(workoutId, name)
            .subscribe()
            .disposeOnPause()

        view?.setWorkoutName(name)
    }

    override fun onDeleteWorkoutBtnClick() {
        workoutInteractor.deleteWorkoutById(workoutId)
            .observeOnMain()
            .subscribeBy(
                onComplete = { view?.onBackPressed() }
            )
            .disposeOnPause()
    }

    override fun onStartWorkoutBtnClick() {
        view?.startTimerFragment()
    }

    override fun onRoundItemClick(item: Round) {
        intervalId = item.id
        view?.showRoundEditDialog(item)
    }

    private fun fetchIntervalList() {
        roundInteractor.observeRounds(workoutId)
            .observeOnMain()
            .subscribeBy(
                onNext = {
                    view?.renderRoundList(it)
                }
            )
            .disposeOnPause()
    }

    private fun getCurrentWorkout() {
        workoutInteractor.getWorkoutById(workoutId)
            .observeOnMain()
            .subscribeBy(
                onSuccess = {
                    view?.setWorkoutName(it.name)
                    view?.setSilentMode(it.isSilentMode)
                }
            )
            .disposeOnPause()
    }
}
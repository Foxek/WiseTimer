package com.foxek.simpletimer.presentation.round

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter

import javax.inject.Inject

class RoundPresenter @Inject constructor(
    private val roundInteractor: RoundInteractor,
    private val workoutInteractor: WorkoutInteractor
) : BasePresenter<RoundContact.View>(), RoundContact.Presenter {

    private var roundId = NO_ID_INT
    private var rounds: List<Round> = emptyList()

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
            .subscribe { isSilentMode -> view?.setSilentMode(isSilentMode) }
            .disposeOnPause()
    }

    override fun onAddRoundBtnClick() {
        view?.showRoundCreateDialog()
    }

    override fun onSaveRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int) {
        roundInteractor.updateRound(workoutId, roundId,  name, type, workTime, restTime)
            .subscribe()
            .disposeOnPause()
    }

    override fun onCreateRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int) {
        roundInteractor.addRound(Round(name, type, workTime, restTime, workoutId, 0))
            .subscribe()
            .disposeOnPause()
    }

    override fun onDeleteRoundBtnClick() {
        roundInteractor.deleteRound(workoutId, roundId)
            .subscribe()
            .disposeOnPause()
    }

    override fun onStartWorkoutBtnClick() {
        view?.startTimerFragment()
    }

    override fun onRoundItemClick(item: Round) {
        roundId = item.id
        view?.showRoundEditDialog(item)
    }

    private fun fetchIntervalList() {
        roundInteractor.observeRounds(workoutId)
            .observeOnMain()
            .subscribe {
                rounds = it
                view?.renderRoundList(rounds)
            }
            .disposeOnPause()
    }

    private fun getCurrentWorkout() {
        workoutInteractor.getWorkoutById(workoutId)
            .observeOnMain()
            .subscribe { workout ->
                view?.setWorkoutName(workout.name)
                view?.setSilentMode(workout.isSilentMode)
            }
            .disposeOnPause()
    }
}
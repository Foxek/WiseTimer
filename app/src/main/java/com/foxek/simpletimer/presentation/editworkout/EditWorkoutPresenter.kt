package com.foxek.simpletimer.presentation.editworkout

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter
import javax.inject.Inject

class EditWorkoutPresenter @Inject constructor(
    private val roundInteractor: RoundInteractor,
    private val workoutInteractor: WorkoutInteractor
) : BasePresenter<EditWorkoutContract.View>(), EditWorkoutContract.Presenter {

    override var workoutId: Int = Constants.NO_ID_INT

    override fun onViewResumed() {
        getCurrentWorkout()
        fetchIntervalList()
    }

    override fun onSaveBtnClick(rounds: List<Round>, workoutName: String) {
        workoutInteractor.updateWorkoutName(workoutId, workoutName)
            .subscribe { view?.onBackPressed() }
            .disposeOnPause()
    }

    private fun fetchIntervalList() {
        roundInteractor.observeRounds(workoutId)
            .observeOnMain()
            .subscribe { view?.renderRoundList(it) }
            .disposeOnPause()
    }

    private fun getCurrentWorkout() {
        workoutInteractor.getWorkoutById(workoutId)
            .observeOnMain()
            .subscribe { workout -> view?.setWorkoutName(workout.name) }
            .disposeOnPause()
    }
}
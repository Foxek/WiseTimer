package com.foxek.simpletimer.presentation.editworkout

import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.base.BasePresenter
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class EditWorkoutPresenter @Inject constructor(
    private val roundInteractor: RoundInteractor,
    private val workoutInteractor: WorkoutInteractor
) : BasePresenter<EditWorkoutContract.View>(), EditWorkoutContract.Presenter {

    override var workoutId: Int = Constants.NO_ID_INT

    override fun viewIsReady() {
        view?.setupRoundAdapter()

        getCurrentWorkout()
        fetchIntervalList()
    }

    override fun onSaveBtnClick(rounds: List<Round>, workoutName: String) {
        workoutInteractor.updateWorkoutName(workoutId, workoutName)
            .subscribeBy(
                onComplete = { view?.startRoundFragment() }
            )
            .disposeOnDestroy()
    }

    private fun fetchIntervalList() {
        roundInteractor.observeRounds(workoutId)
            .observeOnMain()
            .subscribeBy(
                onNext = {
                    view?.renderRoundList(it)
                }
            )
            .disposeOnDestroy()
    }

    private fun getCurrentWorkout() {
        workoutInteractor.getWorkoutById(workoutId)
            .observeOnMain()
            .subscribeBy(
                onSuccess = {
                    view?.setWorkoutName(it.name)
                }
            )
            .disposeOnDestroy()
    }
}
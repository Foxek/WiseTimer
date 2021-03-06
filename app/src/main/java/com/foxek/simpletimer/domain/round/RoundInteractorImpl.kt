package com.foxek.simpletimer.domain.round

import com.foxek.simpletimer.data.database.TimerDAO
import com.foxek.simpletimer.data.model.Round

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class RoundInteractorImpl @Inject constructor(
    private val timerDAO: TimerDAO
) : RoundInteractor {

    override fun observeRounds(workoutId: Int): Flowable<List<Round>> {
        return timerDAO.observeRounds(workoutId)
            .map {
                it.sortedBy { it.positionInWorkout }
            }
    }

    override fun getRounds(workoutId: Int): Single<List<Round>> {
        return timerDAO.getRounds(workoutId)
            .map {
                it.sortedBy { it.positionInWorkout }
            }
    }

    override fun addRound(round: Round): Completable {
        return timerDAO.getLastPositionInWorkout(round.workoutId)
            .flatMapCompletable {
                round.positionInWorkout = it + 1
                Completable.fromAction { timerDAO.addRound(round) }
            }
    }

    override fun updateRound(
        workoutId: Int,
        intervalId: Int,
        intervalName: String,
        intervalType: Int,
        workTime: Int,
        restTime: Int
    ): Completable {
        return timerDAO.getRoundById(intervalId, workoutId)
            .flatMapCompletable {
                it.name = intervalName
                it.type = intervalType
                it.workInterval = workTime
                it.restInterval = restTime
                Completable.fromAction { timerDAO.updateRound(it) }
            }
    }

    override fun deleteRound(workoutId: Int, roundId: Int): Completable {
        return timerDAO.getNumberOfRoundForWorkout(workoutId)
            .filter { it != 1 }
            .flatMapCompletable {
                Completable.fromAction { timerDAO.deleteRoundAndRearrangeOthers(roundId, workoutId) }
            }
    }
}
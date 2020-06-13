package com.foxek.simpletimer.domain.round

import com.foxek.simpletimer.data.model.Round
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RoundInteractor {

    fun observeRounds(workoutId: Int): Flowable<List<Round>>

    fun getRounds(workoutId: Int): Single<List<Round>>

    fun addRound(round: Round): Completable

    fun updateRound(
        workoutId: Int,
        intervalId: Int,
        intervalName: String,
        intervalType: Int,
        workTime: Int,
        restTime: Int
    ): Completable

    fun deleteRound(workoutId: Int, roundId: Int): Completable
}
package com.foxek.simpletimer.presentation.editworkout

import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.MvpPresenter
import com.foxek.simpletimer.presentation.base.MvpView

interface EditWorkoutContract {

    interface View : MvpView {
        fun setupRoundAdapter()
        fun renderRoundList(rounds: List<Round>)
        fun setWorkoutName(name: String)
        fun startRoundFragment()
    }

    interface Presenter : MvpPresenter<View> {
        var workoutId: Int

        fun onSaveBtnClick(rounds: List<Round>, workoutName: String)
    }
}
package com.foxek.simpletimer.presentation.editworkout

import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseContract

interface EditWorkoutContract {

    interface View : BaseContract.View {

        fun renderRoundList(rounds: List<Round>)

        fun setWorkoutName(name: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var workoutId: Int

        fun onSaveWorkoutBtnClick(rounds: List<Round>, workoutName: String)
        fun onDeleteWorkoutBtnClick()
    }
}
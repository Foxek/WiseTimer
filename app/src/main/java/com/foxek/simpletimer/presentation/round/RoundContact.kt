package com.foxek.simpletimer.presentation.round

import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseContract

interface RoundContact {

    interface View : BaseContract.View {

        fun renderRoundList(roundList: List<Round>)

        fun setSilentMode(state: Boolean)

        fun setWorkoutName(name: String)

        fun startTimerFragment()

        fun showRoundEditDialog(round: Round)

        fun showRoundCreateDialog()

        fun startEditWorkoutFragment(workoutId: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {

        var workoutId: Int

        fun onSaveRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int)

        fun onCreateRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int)

        fun onDeleteRoundBtnClick()

        fun onEditWorkoutBtnClick()

        fun onStartWorkoutBtnClick()

        fun onToggleSilentModeBtnClick()

        fun onAddRoundBtnClick()

        fun onRoundItemClick(item: Round)
    }
}
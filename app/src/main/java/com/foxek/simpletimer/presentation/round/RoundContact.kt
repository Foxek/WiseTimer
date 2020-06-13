package com.foxek.simpletimer.presentation.round

import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.MvpPresenter
import com.foxek.simpletimer.presentation.base.MvpView

interface RoundContact {

    interface View : MvpView {

        fun setRoundList()

        fun renderRoundList(roundList: List<Round>)

        fun setSilentMode(state: Boolean)

        fun setWorkoutName(name: String)

        fun startWorkoutActivity()

        fun startTimerActivity()

        fun showRoundEditDialog(round: Round)

        fun showRoundCreateDialog()

        fun showWorkoutEditDialog()
    }

    interface Presenter : MvpPresenter<View> {

        var workoutId: Int

        fun onSaveRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int)

        fun onCreateRoundBtnClick(name: String, type: Int, workTime: Int, restTime: Int)

        fun onDeleteRoundBtnClick()

        fun onSaveWorkoutBtnClick(name: String)

        fun onEditWorkoutBtnClick()

        fun onDeleteWorkoutBtnClick()

        fun onStartWorkoutBtnClick()

        fun onToggleSilentModeBtnClick()

        fun onAddRoundBtnClick()

        fun onRoundItemClick(item: Round)
    }
}
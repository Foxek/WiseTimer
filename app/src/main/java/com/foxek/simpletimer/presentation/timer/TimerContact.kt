package com.foxek.simpletimer.presentation.timer

interface TimerContact {

    interface ServiceCallback {

        fun startWorkoutActivity()

        fun showTimerState(buttonState: Int, isRestartAllowed: Boolean)

        fun showCurrentIntervalTime(time: String)

        fun showRoundType(type: Int)

        fun showRoundInfo(currentName: String, nextName: String, number: String)
    }
}
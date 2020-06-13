package com.foxek.simpletimer.presentation.timer

interface TimerContact {

    interface ServiceCallback {

        fun startWorkoutActivity()

        fun showPauseInterface()

        fun showPlayInterface()

        fun showCurrentIntervalTime(time: String)

        fun showRoundType(type: Int)

        fun showRoundInfo(currentName: String, nextName: String, number: String)
    }
}
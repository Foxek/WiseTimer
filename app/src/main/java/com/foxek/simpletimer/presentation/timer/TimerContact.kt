package com.foxek.simpletimer.presentation.timer

interface TimerContact {

    interface ServiceCallback {

        fun startWorkoutActivity()

        fun showPauseInterface()

        fun showPlayInterface()

        fun showCurrentIntervalTime(time: String)

        fun showIntervalType(type: Int)

        fun showIntervalInfo(name: String, number: String)
    }
}
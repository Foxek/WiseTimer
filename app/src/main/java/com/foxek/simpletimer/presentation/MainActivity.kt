package com.foxek.simpletimer.presentation

import android.os.Bundle
import com.foxek.simpletimer.presentation.base.BaseActivity
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.timer.TimerFragment
import com.foxek.simpletimer.presentation.timer.TimerService
import com.foxek.simpletimer.presentation.workout.WorkoutFragment
import com.foxek.simpletimer.common.utils.ServiceTools

class MainActivity : BaseActivity() {
    override var fragment: BaseFragment = WorkoutFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ServiceTools.isServiceRunning(this, TimerService::class.java)) {
            replaceFragment(TimerFragment(), null)
        }
    }
}

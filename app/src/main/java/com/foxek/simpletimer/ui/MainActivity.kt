package com.foxek.simpletimer.ui

import android.os.Bundle
import com.foxek.simpletimer.ui.base.BaseActivity
import com.foxek.simpletimer.ui.base.BaseFragment
import com.foxek.simpletimer.ui.timer.TimerFragment
import com.foxek.simpletimer.ui.timer.TimerService
import com.foxek.simpletimer.ui.workout.WorkoutFragment
import com.foxek.simpletimer.utils.Constants.ACTION_OPEN_TIMER
import com.foxek.simpletimer.utils.ServiceTools

class MainActivity : BaseActivity() {
    override var fragment: BaseFragment = WorkoutFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ServiceTools.isServiceRunning(this, TimerService::class.java)) {
            replaceFragment(TimerFragment(), null)
        }
    }
}

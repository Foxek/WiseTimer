package com.foxek.simpletimer.presentation

import android.os.Bundle
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.extensions.transaction
import com.foxek.simpletimer.common.utils.isServiceRunning
import com.foxek.simpletimer.presentation.base.BaseActivity
import com.foxek.simpletimer.presentation.timer.TimerFragment
import com.foxek.simpletimer.presentation.timer.TimerService
import com.foxek.simpletimer.presentation.workout.WorkoutFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.transaction {
            add(R.id.container, WorkoutFragment.getInstance())
        }

        if (isServiceRunning(this, TimerService::class.java)) {
            supportFragmentManager.transaction {
                val fragment = TimerFragment.getInstance()
                replace(R.id.container, fragment)
                addToBackStack(fragment.tag)
            }
        }
    }

    override fun onBackPressed() {
        if (onBackPressedConsumedByFragment()) return

        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            activityFinish()
        }
    }
}

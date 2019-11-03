package com.foxek.simpletimer.ui

import com.foxek.simpletimer.ui.base.BaseActivity
import com.foxek.simpletimer.ui.base.BaseFragment
import com.foxek.simpletimer.ui.workout.WorkoutFragment

class MainActivity : BaseActivity() {
    override var fragment: BaseFragment = WorkoutFragment()
}

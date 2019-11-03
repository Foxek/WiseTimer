package com.foxek.simpletimer.ui.timer

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseFragment

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_timer.*

class TimerFragment : BaseFragment(), TimerContact.View {

    override val layoutId = R.layout.activity_timer

    @Inject
    lateinit var presenter: TimerContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executeInActivity { activityComponent?.inject(this@TimerFragment) }

        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            workoutName.text = it.getString(EXTRA_WORKOUT_NAME)
            presenter.prepareIntervals(it.getInt(EXTRA_WORKOUT_ID, 0))
        }

        presenter.viewIsReady()

        pauseButton.setOnClickListener { presenter.pauseButtonClicked() }
        resetButton.setOnClickListener { presenter.resetButtonClicked() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun startWorkoutActivity() {
        onBackPressed()
    }

    override fun showPauseInterface() {
        resetButton.visibility = View.VISIBLE
        pauseButton.setText(R.string.timer_continue_button)
    }

    override fun showPlayInterface() {
        resetButton.visibility = View.GONE
        pauseButton.setText(R.string.timer_pause_button)
    }

    override fun showCurrentCounter(time: String) {
        counterView.text = time
    }

    override fun showCounterType(type: Int) {
        counterType.setText(type)
    }


    override fun showCounterNumber(number: String) {
        counterNumber.text = number
    }

    override fun showCounterName(name: String) {
        if (name == EMPTY) {
            intervalName.visibility = View.GONE
        } else {
            intervalName.visibility = View.VISIBLE
            intervalName.text = name
        }
    }

}
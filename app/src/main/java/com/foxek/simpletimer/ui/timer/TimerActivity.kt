package com.foxek.simpletimer.ui.timer

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseActivity

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_timer.*

class TimerActivity : BaseActivity(), TimerContact.View {

    @Inject
    lateinit var presenter: TimerContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val intent = intent
        workoutName.text = intent.getStringExtra(EXTRA_WORKOUT_NAME)

        activityComponent?.inject(this)
        presenter.attachView(this)

        presenter.prepareIntervals(intent.getIntExtra(EXTRA_WORKOUT_ID, 0))
        presenter.viewIsReady()

        pauseButton.setOnClickListener { presenter.pauseButtonClicked() }
        resetButton.setOnClickListener { presenter.resetButtonClicked() }
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

    override fun startWorkoutActivity() {
        finish()
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
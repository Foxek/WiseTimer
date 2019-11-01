package com.foxek.simpletimer.ui.interval

import android.content.Intent
import android.os.Bundle

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseActivity
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog
import com.foxek.simpletimer.ui.timer.TimerActivity

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.data.model.Interval

import com.foxek.simpletimer.ui.interval.adapter.IntervalAdapter

import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_interval.*

class IntervalActivity : BaseActivity(), IntervalContact.View, IntervalAdapter.Callback {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    @Inject
    lateinit var viewAdapter: IntervalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval)

        activityComponent?.inject(this)

        presenter.attachView(this)
        presenter.viewIsReady(intent.getIntExtra(EXTRA_WORKOUT_ID, 0))

        backButton.setOnClickListener { onBackPressed() }
        editButton.setOnClickListener { presenter.editWorkoutButtonClicked() }
        volumeButton.setOnClickListener { presenter.changeVolumeButtonClicked() }
        addIntervalButton.setOnClickListener { presenter.addIntervalButtonClicked() }
        startButton.setOnClickListener { presenter.startWorkoutButtonClicked() }
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun renderIntervalList(intervalList: List<Interval>) {
        viewAdapter.submitList(intervalList)
    }

    override fun setWorkoutName(name: String) {
        workoutName.text = name
    }

    override fun setIntervalList() {
        viewAdapter.setCallback(this)

        intervalList.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }
    }

    override fun setVolumeState(state: Boolean) {
        if (state)
            volumeButton.setImageResource(R.drawable.ic_menu_volume_on_white)
        else
            volumeButton.setImageResource(R.drawable.ic_menu_volume_off_white)
    }

    override fun showIntervalEditDialog(name: String, workTime: Int, restTime: Int) {
        showDialog(IntervalEditDialog.newInstance(name, workTime, restTime))
    }

    override fun showIntervalCreateDialog() {
        showDialog(IntervalCreateDialog.newInstance())
    }

    override fun showWorkoutEditDialog() {
        showDialog(WorkoutEditDialog.newInstance(intent.getStringExtra(EXTRA_WORKOUT_NAME)))
    }

    override fun startWorkoutActivity() {
        onBackPressed()
    }

    override fun startTimerActivity() {
        val timerIntent = Intent(this, TimerActivity::class.java)
        timerIntent
                .putExtra(EXTRA_WORKOUT_ID, intent.getIntExtra(EXTRA_WORKOUT_ID, 0))
                .putExtra("workout_name", intent.getStringExtra("workout_name"))
        startActivity(timerIntent)
        finish()
    }

    override fun onListItemClick(item: Interval) {
        presenter.intervalItemClicked(item)
    }
}
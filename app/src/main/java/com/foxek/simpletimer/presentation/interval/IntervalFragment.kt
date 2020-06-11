package com.foxek.simpletimer.presentation.interval

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.presentation.interval.dialog.IntervalCreateDialog
import com.foxek.simpletimer.presentation.interval.dialog.IntervalEditDialog
import com.foxek.simpletimer.presentation.interval.dialog.WorkoutEditDialog

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.presentation.base.BaseFragment

import com.foxek.simpletimer.presentation.timer.TimerFragment
import com.foxek.simpletimer.presentation.timer.TimerService
import com.foxek.simpletimer.common.utils.Constants.ACTION_START

import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.fragment_interval.*

class IntervalFragment : BaseFragment(), IntervalContact.View {

    override val layoutId = R.layout.fragment_interval

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    private val intervalAdapter: IntervalAdapter by lazy { IntervalAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component?.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { presenter.workoutId = (it.getInt(EXTRA_WORKOUT_ID, 0)) }

        fragment_interval_back_btn.setOnClickListener { onBackPressed() }
        fragment_interval_edit_btn.setOnClickListener { presenter.editWorkoutButtonClicked() }
        fragment_interval_volume_btn.setOnClickListener { presenter.changeVolumeButtonClicked() }
        fragment_interval_add_btn.setOnClickListener { presenter.addIntervalButtonClicked() }
        fragment_interval_start_btn.setOnClickListener { presenter.startWorkoutButtonClicked() }

        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun renderIntervalList(intervalList: List<Interval>) {
        intervalAdapter.setItems(intervalList)
    }

    override fun setWorkoutName(name: String) {
        fragment_interval_workout_name.text = name
    }

    override fun setIntervalList() {
        fragment_interval_list.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = intervalAdapter.apply {
                clickListener = { presenter.intervalItemClicked(it) }
            }
        }
    }

    override fun setVolumeState(state: Boolean) {
        if (state)
            fragment_interval_volume_btn.setImageResource(R.drawable.ic_menu_volume_on_white)
        else
            fragment_interval_volume_btn.setImageResource(R.drawable.ic_menu_volume_off_white)
    }

    override fun showIntervalEditDialog(interval: Interval) {
        showDialog(IntervalEditDialog.newInstance(interval))
    }

    override fun showIntervalCreateDialog() {
        showDialog(IntervalCreateDialog.newInstance())
    }

    override fun showWorkoutEditDialog() {
        showDialog(WorkoutEditDialog.newInstance(arguments?.getString(EXTRA_WORKOUT_NAME)))
    }

    override fun startWorkoutActivity() {
        onBackPressed()
    }

    override fun startTimerActivity() {
        close()

        val intent = Intent(context, TimerService::class.java).apply {
            action = ACTION_START
            putExtra(EXTRA_WORKOUT_ID, arguments?.getInt(EXTRA_WORKOUT_ID, 0))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intent)
        } else {
            context?.startService(intent)
        }

        arguments?.putString(ACTION_START, ACTION_START)
        executeInActivity { replaceFragment(TimerFragment(), arguments) }
    }
}
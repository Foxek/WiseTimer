package com.foxek.simpletimer.ui.workout

import android.content.Intent
import android.os.Bundle

import com.foxek.simpletimer.ui.base.BaseActivity
import com.foxek.simpletimer.ui.interval.IntervalActivity
import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.data.model.Workout

import com.foxek.simpletimer.ui.workout.adapter.WorkoutAdapter

import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_workout.*

class WorkoutActivity : BaseActivity(), WorkoutContact.View,WorkoutAdapter.Callback {

    @Inject
    lateinit var presenter: WorkoutContact.Presenter

    @Inject
    lateinit var viewAdapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        activityComponent?.inject(this)

        presenter.attachView(this)
        presenter.viewIsReady()

        createButton.setOnClickListener {
            presenter.createButtonClicked()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun startIntervalActivity(position: Int, name: String) {
        val intent = Intent(this, IntervalActivity::class.java)
        intent.putExtra(EXTRA_WORKOUT_ID, position)
                .putExtra(EXTRA_WORKOUT_NAME, name)
        startActivity(intent)
    }

    override fun setWorkoutList() {
        viewAdapter.setCallback(this)
        workoutList.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = viewAdapter
        }
    }

    override fun renderWorkoutList(workoutList: List<Workout>) {
        viewAdapter.submitList(workoutList)
    }

    override fun showCreateDialog() {
        showDialog(WorkoutCreateDialog.newInstance())
    }
    override fun onListItemClick(workout: Workout) {
        presenter.onListItemClicked(workout)
    }
}
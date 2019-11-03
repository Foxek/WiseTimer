package com.foxek.simpletimer.ui.workout

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.ui.interval.IntervalFragment
import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.ui.base.BaseFragment

import com.foxek.simpletimer.ui.workout.adapter.WorkoutAdapter

import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_workout.*

class WorkoutFragment : BaseFragment(), WorkoutContact.View, WorkoutAdapter.Callback {

    override val layoutId = R.layout.activity_workout

    @Inject
    lateinit var presenter: WorkoutContact.Presenter

    @Inject
    lateinit var viewAdapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executeInActivity {
            activityComponent?.inject(this@WorkoutFragment)
        }
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.viewIsReady()

        createButton.setOnClickListener {
            presenter.createButtonClicked()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun startIntervalActivity(position: Int, name: String) {
        val args =  Bundle().apply {
            putInt(EXTRA_WORKOUT_ID, position)
            putString(EXTRA_WORKOUT_NAME, name)
        }
        val fragment = IntervalFragment()
        fragment.arguments = args

        executeInActivity {
            replaceFragment(fragment)
        }
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
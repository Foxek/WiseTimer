package com.foxek.simpletimer.ui.workout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.ui.base.BaseFragment
import com.foxek.simpletimer.ui.interval.IntervalFragment
import com.foxek.simpletimer.ui.workout.adapter.WorkoutAdapter
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.activity_workout.*
import javax.inject.Inject


class WorkoutFragment : BaseFragment(), WorkoutContact.View, WorkoutAdapter.Callback {

    override val layoutId = R.layout.activity_workout

    @Inject
    lateinit var presenter: WorkoutContact.Presenter

    @Inject
    lateinit var viewAdapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component?.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.viewIsReady()

        createButton.setOnClickListener {
            presenter.createButtonClicked()
        }

        workoutList.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = viewAdapter
        }

        viewAdapter.setCallback(this)
    }

    override fun startIntervalFragment(id: Int, name: String?) {
        val args = Bundle().apply {
            putInt(EXTRA_WORKOUT_ID, id)
            putString(EXTRA_WORKOUT_NAME, name)
        }

        executeInActivity { replaceFragment(IntervalFragment(), args) }
    }

    override fun renderWorkoutList(list: List<Workout>) {
        viewAdapter.submitList(list)
    }

    override fun showCreateDialog() {
        showDialog(WorkoutCreateDialog.newInstance())
    }

    override fun onListItemClick(workout: Workout) {
        presenter.onListItemClicked(workout)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
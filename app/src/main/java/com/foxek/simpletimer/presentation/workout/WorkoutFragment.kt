package com.foxek.simpletimer.presentation.workout

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.os.bundleOf
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.extensions.transaction
import com.foxek.simpletimer.common.utils.Constants.CHANGELOG_VERSION_VALUE
import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.data.UserPreferences
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.di.component.FragmentComponent
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.base.FragmentFactory
import com.foxek.simpletimer.presentation.changelog.ChangeLogDialog
import com.foxek.simpletimer.presentation.round.RoundFragment
import com.foxek.simpletimer.presentation.workout.dialog.WorkoutCreateDialog
import kotlinx.android.synthetic.main.fragment_workout.*
import javax.inject.Inject

class WorkoutFragment : BaseFragment<WorkoutContact.View, WorkoutContact.Presenter>(),
    WorkoutContact.View {

    override val layoutId = R.layout.fragment_workout

    private val adapter: WorkoutAdapter by lazy { WorkoutAdapter() }

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onInject(component: FragmentComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWorkoutList()
        showChangelogDialogIfNeeded()
    }

    override fun attachListeners() {
        fragment_workout_create_btn.setOnClickListener { presenter.onCreateButtonClick() }
    }

    override fun startRoundFragment(workoutId: Int) {
        val payload = bundleOf(EXTRA_WORKOUT_ID to workoutId)
        val fragment = RoundFragment.getInstance(payload)

        fragmentManager?.transaction {
            replace(R.id.container, fragment)
            addToBackStack(fragment.tag)
        }
    }

    override fun renderWorkoutList(items: List<Workout>) {
        adapter.setItems(items)
    }

    override fun showCreateDialog() {
        showDialog(WorkoutCreateDialog.newInstance())
    }

    private fun setupWorkoutList() {
        fragment_workout_list.apply {
            itemAnimator = null
            isNestedScrollingEnabled = false
            adapter = this@WorkoutFragment.adapter.apply {
                clickListener = { presenter.onWorkoutItemClick(it) }
            }
        }
    }

    private fun showChangelogDialogIfNeeded() {
        var changelogVersion = 0

        PreferenceManager.getDefaultSharedPreferences(context).run {
            changelogVersion = userPreferences.changelogVersion
            userPreferences.changelogVersion = CHANGELOG_VERSION_VALUE
        }

        if (changelogVersion < CHANGELOG_VERSION_VALUE) {
            showDialog(ChangeLogDialog())
        }
    }

    companion object : FragmentFactory<WorkoutFragment> {
        override fun getInstance(bundle: Bundle?): WorkoutFragment = WorkoutFragment().apply {
            arguments = bundle
        }
    }
}
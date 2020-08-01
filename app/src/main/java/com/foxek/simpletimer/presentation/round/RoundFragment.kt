package com.foxek.simpletimer.presentation.round


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.utils.Constants.ACTION_START
import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.base.FragmentFactory
import com.foxek.simpletimer.presentation.editworkout.EditWorkoutFragment
import com.foxek.simpletimer.presentation.round.dialog.RoundCreateDialog
import com.foxek.simpletimer.presentation.round.dialog.RoundEditDialog
import com.foxek.simpletimer.presentation.timer.TimerFragment
import com.foxek.simpletimer.presentation.timer.TimerService
import kotlinx.android.synthetic.main.fragment_round.*
import javax.inject.Inject

class RoundFragment : BaseFragment(), RoundContact.View {

    override val layoutId = R.layout.fragment_round

    @Inject
    lateinit var presenter: RoundContact.Presenter

    private val roundAdapter: RoundAdapter by lazy { RoundAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component?.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { presenter.workoutId = (it.getInt(EXTRA_WORKOUT_ID, 0)) }

        fragment_interval_back_btn.setOnClickListener { onBackPressed() }
        fragment_interval_edit_btn.setOnClickListener { presenter.onEditWorkoutBtnClick() }
        fragment_interval_volume_btn.setOnClickListener { presenter.onToggleSilentModeBtnClick() }
        fragment_interval_add_btn.setOnClickListener { presenter.onAddRoundBtnClick() }
        fragment_interval_start_btn.setOnClickListener { presenter.onStartWorkoutBtnClick() }

        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun renderRoundList(roundList: List<Round>) {
        roundAdapter.setItems(roundList)
    }

    override fun setWorkoutName(name: String) {
        fragment_interval_workout_name.text = name
    }

    override fun setupRoundAdapter() {
        fragment_interval_list.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = roundAdapter.apply {
                clickListener = { presenter.onRoundItemClick(it) }
            }
        }
    }

    override fun setSilentMode(state: Boolean) {
        if (state)
            fragment_interval_volume_btn.setImageResource(R.drawable.ic_menu_volume_off_white)
        else
            fragment_interval_volume_btn.setImageResource(R.drawable.ic_menu_volume_on_white)
    }

    override fun showRoundEditDialog(round: Round) {
        showDialog(RoundEditDialog.newInstance(round))
    }

    override fun showRoundCreateDialog() {
        showDialog(RoundCreateDialog.newInstance())
    }

    override fun startEditWorkoutFragment(workoutId: Int) {
        val args = Bundle().apply {
            putInt(EXTRA_WORKOUT_ID, workoutId)
        }

        executeInActivity { replaceFragment(EditWorkoutFragment.getInstance(args)) }
    }

    override fun startWorkoutFragment() {
        onBackPressed()
    }

    override fun startTimerFragment() {
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
        executeInActivity { replaceFragment(TimerFragment.getInstance(arguments)) }
    }

    companion object : FragmentFactory<RoundFragment> {
        override fun getInstance(bundle: Bundle?): RoundFragment = RoundFragment().apply {
            arguments = bundle
        }
    }
}
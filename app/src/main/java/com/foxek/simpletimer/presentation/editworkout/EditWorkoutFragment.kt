package com.foxek.simpletimer.presentation.editworkout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.base.FragmentFactory
import kotlinx.android.synthetic.main.fragment_edit_workout.*
import javax.inject.Inject

class EditWorkoutFragment : BaseFragment(), EditWorkoutContract.View {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) { EditWorkoutAdapter() }

    @Inject
    lateinit var presenter: EditWorkoutContract.Presenter

    override val layoutId: Int = R.layout.fragment_edit_workout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component?.inject(this)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            presenter.workoutId = (it.getInt(Constants.EXTRA_WORKOUT_ID, 0))
        }

        fragment_edit_workout_close_btn.setOnClickListener { startRoundFragment() }
        fragment_edit_workout_save_btn.setOnClickListener {
            presenter.onSaveBtnClick(adapter.getItems(), fragment_edit_workout_name.text.toString())
        }

        presenter.viewIsReady()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun setupRoundAdapter() {
        val callback = ItemTouchHelperCallback(adapter)
        ItemTouchHelper(callback).apply {
            attachToRecyclerView(fragment_edit_workout_list)
        }
        fragment_edit_workout_list.apply {
            itemAnimator = null
            adapter = this@EditWorkoutFragment.adapter
        }
    }

    override fun renderRoundList(rounds: List<Round>) {
        adapter.setItems(rounds)
    }

    override fun setWorkoutName(name: String) {
        fragment_edit_workout_name.setText(name)
    }

    override fun startRoundFragment() {
        onBackPressed()
    }

    companion object : FragmentFactory<EditWorkoutFragment> {
        override fun getInstance(bundle: Bundle?): EditWorkoutFragment = EditWorkoutFragment().apply {
            arguments = bundle
        }
    }
}
package com.foxek.simpletimer.presentation.editworkout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.di.component.FragmentComponent
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.base.FragmentFactory
import kotlinx.android.synthetic.main.fragment_edit_workout.*

class EditWorkoutFragment : BaseFragment<EditWorkoutContract.View, EditWorkoutContract.Presenter>(),
    EditWorkoutContract.View {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) { EditWorkoutAdapter() }

    private lateinit var touchHelper: ItemTouchHelper

    override val layoutId: Int = R.layout.fragment_edit_workout

    override fun onInject(component: FragmentComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.workoutId = requireArguments().getInt(Constants.EXTRA_WORKOUT_ID, 0)

        setupRoundList()
    }

    override fun attachListeners() {
        fragment_edit_workout_close_btn.setOnClickListener { close() }
        fragment_edit_workout_delete_btn.setOnClickListener { presenter.onDeleteWorkoutBtnClick() }
        fragment_edit_workout_save_btn.setOnClickListener {
            presenter.onSaveWorkoutBtnClick(adapter.getItems(), fragment_edit_workout_name.text.toString())
        }
    }

    override fun renderRoundList(rounds: List<Round>) {
        adapter.setItems(rounds)
    }

    override fun closeFragment() {
        close()
    }

    override fun setWorkoutName(name: String) {
        fragment_edit_workout_name.setText(name)
    }

    private fun setupRoundList() {
        touchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        touchHelper.attachToRecyclerView(fragment_edit_workout_list)

        fragment_edit_workout_list.apply {
            itemAnimator = null
            adapter = this@EditWorkoutFragment.adapter.apply {
                onDragListener = { touchHelper.startDrag(it) }
            }
        }
    }

    companion object : FragmentFactory<EditWorkoutFragment> {
        override fun getInstance(bundle: Bundle?): EditWorkoutFragment = EditWorkoutFragment().apply {
            arguments = bundle
        }
    }
}
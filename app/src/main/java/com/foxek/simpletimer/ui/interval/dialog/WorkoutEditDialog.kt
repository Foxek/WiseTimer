package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.dialog_edit_workout.*

class WorkoutEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "WorkoutEditDialog"
    override val layoutId = R.layout.dialog_edit_workout

    companion object {

        fun newInstance(name: String?): WorkoutEditDialog {
            val mWorkoutEditDialog = WorkoutEditDialog()
            val args = Bundle()
            args.putString(EXTRA_WORKOUT_NAME, name)
            mWorkoutEditDialog.arguments = args
            return mWorkoutEditDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etWorkoutName.setText(arguments?.getString(EXTRA_WORKOUT_NAME))
        etWorkoutName.setSelection(etWorkoutName.text.length)

        deleteButton.setOnClickListener {
            dismiss()
            if (showsDialog) {
                presenter.deleteWorkoutButtonClicked()
            }
        }

        saveButton.setOnClickListener {
            dismiss()
            if (checkNotEmpty(etWorkoutName)) {
                presenter.saveWorkoutButtonClicked(etWorkoutName.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repairMemoryLeak(etWorkoutName)
    }
}
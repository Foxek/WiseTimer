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

    override var dialogTitle = R.string.dialog_training_setting_title

    override var dialogDescription = R.string.dialog_training_setting_description

    override fun getLayout() = R.layout.dialog_edit_workout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_edit_workout_field_name.apply {
            setText(arguments?.getString(EXTRA_WORKOUT_NAME))
            setSelection(dialog_edit_workout_field_name.text.length)
        }

        dialog_edit_workout_delete_btn.setOnClickListener {
            dismiss()
            if (showsDialog)
                presenter.deleteWorkoutButtonClicked()
        }

        dialog_edit_workout_save_btn.setOnClickListener {
            dismiss()
            if (checkNotEmpty(dialog_edit_workout_field_name))
                presenter.saveWorkoutButtonClicked(dialog_edit_workout_field_name.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog_edit_workout_field_name.isCursorVisible = false
    }

    companion object {
        fun newInstance(name: String?): WorkoutEditDialog {
            val mWorkoutEditDialog = WorkoutEditDialog()
            mWorkoutEditDialog.arguments = Bundle().apply {
                putString(EXTRA_WORKOUT_NAME, name)
            }
            return mWorkoutEditDialog
        }
    }
}
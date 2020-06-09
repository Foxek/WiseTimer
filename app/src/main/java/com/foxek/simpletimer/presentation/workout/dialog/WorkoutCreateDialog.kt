package com.foxek.simpletimer.presentation.workout.dialog

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.presentation.base.BaseDialog
import com.foxek.simpletimer.presentation.workout.WorkoutContact

import javax.inject.Inject

import kotlinx.android.synthetic.main.dialog_add_workout.*

class WorkoutCreateDialog : BaseDialog() {

    @Inject
    lateinit var presenter: WorkoutContact.Presenter

    override var dialogTitle = R.string.dialog_training_create_title

    override var dialogDescription = R.string.dialog_training_create_description

    override fun getLayout(): Int = R.layout.dialog_add_workout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_add_workout_create_btn.setOnClickListener { createButtonClick() }
    }

    private fun createButtonClick(){
        if (checkNotEmpty(dialog_add_workout_field_name)) {
            dismiss()
            presenter.onSaveButtonClick(dialog_add_workout_field_name.text.toString())
        } else {
            dialog_add_workout_error_hint.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog_add_workout_field_name.isCursorVisible = false
    }

    companion object {
        fun newInstance(): WorkoutCreateDialog = WorkoutCreateDialog()
    }
}

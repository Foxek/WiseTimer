package com.foxek.simpletimer.ui.workout.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.workout.WorkoutContact

import javax.inject.Inject

import butterknife.BindView
import kotlinx.android.synthetic.main.dialog_add_workout.*

class WorkoutCreateDialog : BaseDialog() {

    override val dialogTag = "WorkoutCreateDialog"

    @Inject
    lateinit var presenter: WorkoutContact.Presenter

    companion object {
        fun newInstance(): WorkoutCreateDialog = WorkoutCreateDialog()
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View? {
        val dialogView = inflater.inflate(R.layout.dialog_add_workout, parent, false)

        getActivityComponent()?.inject(this)

        return dialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createButton.setOnClickListener {
            if (workoutEditText.text.toString().isNotEmpty()) {
                workoutEditText.isCursorVisible = false
                dismiss()
                presenter.saveButtonClicked(workoutEditText.text.toString())
            } else {
                errorHint.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        workoutEditText.isCursorVisible = false
    }
}

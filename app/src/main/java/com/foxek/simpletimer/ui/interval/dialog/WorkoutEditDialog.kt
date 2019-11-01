package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import butterknife.OnClick

import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME
import kotlinx.android.synthetic.main.dialog_edit_workout.*

class WorkoutEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "WorkoutEditDialog"

    companion object {

        fun newInstance(workoutName: String): WorkoutEditDialog {
            val mWorkoutEditDialog = WorkoutEditDialog()
            val args = Bundle()
            args.putString(EXTRA_WORKOUT_NAME, workoutName)
            mWorkoutEditDialog.arguments = args
            return mWorkoutEditDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialogView = inflater.inflate(R.layout.dialog_edit_workout, container, false)

        getActivityComponent()?.inject(this)

        dialog?.setCanceledOnTouchOutside(true)

        return dialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etWorkoutName.setText(arguments?.getString(EXTRA_WORKOUT_NAME))
        etWorkoutName.setSelection(etWorkoutName.text.length)

        deleteButton.setOnClickListener {
            if (showsDialog) {
                etWorkoutName.isCursorVisible = false
                dialog?.cancel()
                presenter.deleteWorkoutButtonClicked()
            } else
                dismiss()
        }

        saveButton.setOnClickListener {
            if (etWorkoutName.text.toString().isNotEmpty()) {
                etWorkoutName.isCursorVisible = false
                dismiss()
                presenter.saveWorkoutButtonClicked(etWorkoutName.text.toString())
            } else
                dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        etWorkoutName.isCursorVisible = false
    }
}
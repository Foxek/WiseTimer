package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle

import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.EXTRA_INTERVAL_NAME
import com.foxek.simpletimer.utils.Constants.EXTRA_REST_TIME
import com.foxek.simpletimer.utils.Constants.EXTRA_WORK_TIME
import com.foxek.simpletimer.utils.convertToSeconds
import com.foxek.simpletimer.utils.formatEditTextData
import kotlinx.android.synthetic.main.dialog_edit_interval.*

class IntervalEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "IntervalEditDialog"
    override val layoutId = R.layout.dialog_edit_interval

    companion object {

        fun newInstance(interval: Interval): IntervalEditDialog {
            val dialog = IntervalEditDialog()
            val args = Bundle().apply {
                putInt(EXTRA_WORK_TIME, interval.workTime)
                putInt(EXTRA_REST_TIME, interval.restTime)
                putString(EXTRA_INTERVAL_NAME, interval.name)
            }

            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareEditText(
                arguments!!.getString(EXTRA_INTERVAL_NAME)!!,
                arguments!!.getInt(EXTRA_WORK_TIME),
                arguments!!.getInt(EXTRA_REST_TIME)
        )

        cbNameVisibility.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etIntervalName.setText(EMPTY)
                etIntervalName.visibility = View.GONE
            } else {
                etIntervalName.visibility = View.VISIBLE
            }
        }
        saveButton.setOnClickListener { onSaveButtonClick() }
        deleteButton.setOnClickListener { onDeleteButtonClick() }

        repeatGroup.visibility = View.GONE
    }

    private fun prepareEditText(name: String, work_time: Int, rest_time: Int) {

        if (name != EMPTY) {
            cbNameVisibility.isChecked = false
            etIntervalName.visibility = View.VISIBLE
            etIntervalName.setText(name)
        }

        etWorkMinutes.setText(formatEditTextData(work_time / 60))
        etWorkSeconds.setText(formatEditTextData(work_time % 60))

        etRestMinutes.setText(formatEditTextData(rest_time / 60))
        etRestSeconds.setText(formatEditTextData(rest_time % 60))
    }

    private fun onSaveButtonClick() {
        var workTime: Int
        var restTime: Int
        var name = EMPTY

        if (checkNotEmpty(etWorkMinutes, etWorkSeconds)) {
            workTime = convertToSeconds(etWorkMinutes.text.toString(), etWorkSeconds.text.toString())
            if (workTime == 0) workTime = 1
        } else
            workTime = 1


        if (checkNotEmpty(etRestMinutes, etRestSeconds)) {
            restTime = convertToSeconds(etRestMinutes.text.toString(), etRestSeconds.text.toString())
            if (restTime == 0) restTime = 1
        } else
            restTime = 1

        if (checkNotEmpty(etIntervalName))
            name = etIntervalName.text.toString()

        presenter.saveIntervalButtonClicked(name, workTime, restTime)
        dismiss()
    }

    private fun onDeleteButtonClick() {
        dismiss()
        presenter.deleteIntervalButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repairMemoryLeak(
                etWorkMinutes, etWorkSeconds,
                etRestMinutes, etRestSeconds,
                etRepeatsCount, etIntervalName
        )
    }
}
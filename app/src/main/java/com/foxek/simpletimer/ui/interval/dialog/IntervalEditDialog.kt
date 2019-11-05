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

            dialog.arguments = Bundle().apply {
                putInt(EXTRA_WORK_TIME, interval.workTime)
                putInt(EXTRA_REST_TIME, interval.restTime)
                putString(EXTRA_INTERVAL_NAME, interval.name)
            }

            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            prepareEditText(
                    it.getString(EXTRA_INTERVAL_NAME),
                    it.getInt(EXTRA_WORK_TIME),
                    it.getInt(EXTRA_REST_TIME)
            )
        }

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

        repeatDescGroup.visibility = View.GONE
    }

    private fun prepareEditText(name: String?, work_time: Int, rest_time: Int) {

        if (name != EMPTY) {
            cbNameVisibility.isChecked = false
            etIntervalName.visibility = View.VISIBLE
            etIntervalName.setText(name)
        }

        etWorkMin.setText(formatEditTextData(work_time / 60))
        etWorkSec.setText(formatEditTextData(work_time % 60))
        etRestMin.setText(formatEditTextData(rest_time / 60))
        etRestSec.setText(formatEditTextData(rest_time % 60))
    }

    private fun onSaveButtonClick() {
        val workTime = convertToSeconds(etWorkMin.text.toString(), etWorkSec.text.toString())
        val restTime = convertToSeconds(etRestMin.text.toString(), etRestSec.text.toString())
        var name = EMPTY

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
        repairMemoryLeak(etWorkMin, etWorkSec, etRestMin, etRestSec, etRepeats, etIntervalName)
    }
}
package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.convertToSeconds
import com.foxek.simpletimer.utils.formatEditTextData
import kotlinx.android.synthetic.main.dialog_edit_interval.*
import kotlinx.android.synthetic.main.dialog_edit_interval.deleteButton
import kotlinx.android.synthetic.main.dialog_edit_interval.dialogTitle
import kotlinx.android.synthetic.main.dialog_edit_interval.saveButton

class IntervalCreateDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "IntervalCreateDialog"
    override val layoutId = R.layout.dialog_edit_interval

    companion object {
        fun newInstance(): IntervalCreateDialog = IntervalCreateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteButton.visibility = View.GONE
        dialogTitle.setText(R.string.dialog_interval_create_title)

        cbRepeatVisibility.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etRepeats.visibility = View.VISIBLE
                repeatName.visibility = View.VISIBLE
            } else{
                etRepeats.visibility = View.GONE
                repeatName.visibility = View.GONE
            }
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

        prepareEditText()
    }

    private fun prepareEditText() {
        etWorkMin.setText(formatEditTextData(0))
        etWorkSec.setText(formatEditTextData(0))

        etRestMin.setText(formatEditTextData(0))
        etRestSec.setText(formatEditTextData(0))

        etRepeats.setText("1")
    }

    private fun onSaveButtonClick() {
        val workTime = convertToSeconds(etWorkMin.text.toString(), etWorkSec.text.toString())
        val restTime = convertToSeconds(etRestMin.text.toString(), etRestSec.text.toString())
        val repeat = if (checkNotEmpty(etRepeats)) etRepeats.text.toString().toInt() else 1
        var name = EMPTY

        if (checkNotEmpty(etIntervalName))
            name = etIntervalName.text.toString()

        for (i in 1..repeat)
            presenter.createIntervalButtonClicked(name, workTime, restTime)

        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        repairMemoryLeak(etWorkMin, etWorkSec, etRestMin, etRestSec, etRepeats, etIntervalName)
    }
}
package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.convertToSeconds
import com.foxek.simpletimer.utils.formatEditTextData
import kotlinx.android.synthetic.main.dialog_edit_interval.*

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
                repeatName.visibility = View.VISIBLE
                etRepeatsCount.visibility = View.VISIBLE
            } else {
                repeatName.visibility = View.GONE
                etRepeatsCount.visibility = View.GONE
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
        etWorkMinutes.setText(formatEditTextData(0))
        etWorkSeconds.setText(formatEditTextData(0))

        etRestMinutes.setText(formatEditTextData(0))
        etRestSeconds.setText(formatEditTextData(0))

        etRepeatsCount.setText("1")
    }

    private fun onSaveButtonClick() {
        var workTime: Int
        var restTime: Int
        var repeat: Int
        var name = EMPTY

        if (etWorkMinutes.text.toString() != EMPTY && etWorkSeconds.text.toString() != EMPTY) {
            workTime = convertToSeconds(etWorkMinutes.text.toString(), etWorkSeconds.text.toString())
            if (workTime == 0) workTime = 1
        } else
            workTime = 1


        if (etRestMinutes.text.toString() != EMPTY && etRestSeconds.text.toString() != EMPTY) {
            restTime = convertToSeconds(etRestMinutes.text.toString(), etRestSeconds.text.toString())
            if (restTime == 0) restTime = 1
        } else
            restTime = 1

        if (etRepeatsCount.text.toString() != EMPTY) {
            repeat = Integer.valueOf(etRepeatsCount.text.toString())
            if (repeat == 0) repeat = 1
        } else
            repeat = 1

        if (etIntervalName.text.toString() != EMPTY)
            name = etIntervalName.text.toString()

        for (i in 1..repeat)
            presenter.createIntervalButtonClicked(name, workTime, restTime)

        dismiss()
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
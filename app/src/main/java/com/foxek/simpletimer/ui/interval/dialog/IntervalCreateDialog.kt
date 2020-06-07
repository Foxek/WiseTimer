package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import kotlinx.android.synthetic.main.dialog_edit_interval.*
import kotlinx.android.synthetic.main.dialog_edit_interval.deleteButton
import kotlinx.android.synthetic.main.dialog_edit_interval.saveButton
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.foxek.simpletimer.utils.Constants.WITHOUT_REST_TYPE
import com.foxek.simpletimer.utils.Constants.WITH_REST_TYPE


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
        dialogTitle.text = resources.getString(R.string.dialog_interval_create_title)

        cbRepeats.setOnCheckedChangeListener { _, isChecked -> onRepeatsCheckBoxClick(isChecked) }
        cbName.setOnCheckedChangeListener { _, isChecked -> onNameCheckBoxClick(isChecked) }

        saveButton.setOnClickListener { onSaveButtonClick() }

        setTypeSpinner()
        prepareEditText()
    }

    private fun prepareEditText() {
        etWork.setValue(0)
        etRest.setValue(0)

        etRepeats.setText("1")
    }

    private fun setTypeSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.type_list,
            R.layout.view_spinner_selected
        )
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner_drop)
        typeSpinner.adapter = arrayAdapter

        typeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    WITH_REST_TYPE -> {
                        restGroup.visibility = View.VISIBLE
                        workGroup.visibility = View.VISIBLE
                    }
                    WITHOUT_REST_TYPE -> {
                        restGroup.visibility = View.GONE
                        workGroup.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun onNameCheckBoxClick(isChecked: Boolean) {
        if (isChecked) {
            etName.setText(EMPTY)
            etName.visibility = View.GONE
        } else {
            etName.visibility = View.VISIBLE
        }
    }

    private fun onRepeatsCheckBoxClick(isChecked: Boolean) {
        if (isChecked) {
            repeatsGroup.visibility = View.VISIBLE
        } else {
            repeatsGroup.visibility = View.GONE
        }
    }

    private fun onSaveButtonClick() {

        val repeat = if (checkNotEmpty(etRepeats)) etRepeats.text.toString().toInt() else 1
        var name = EMPTY

        val type = typeSpinner.selectedItemPosition

        if (checkNotEmpty(etName))
            name = etName.text.toString()

        for (i in 1..repeat)
            presenter.createIntervalButtonClicked(name, type, etWork.getValue(), etRest.getValue())

        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        etRepeats.isCursorVisible = false
        etName.isCursorVisible = false
    }
}
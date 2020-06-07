package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle

import android.view.View
import android.widget.AdapterView
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact
import com.foxek.simpletimer.utils.Constants

import javax.inject.Inject

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.EXTRA_INTERVAL_NAME
import com.foxek.simpletimer.utils.Constants.EXTRA_REST_TIME
import com.foxek.simpletimer.utils.Constants.EXTRA_TYPE
import com.foxek.simpletimer.utils.Constants.EXTRA_WORK_TIME
import kotlinx.android.synthetic.main.dialog_edit_interval.*
import android.widget.ArrayAdapter


class IntervalEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "IntervalEditDialog"
    override val layoutId = R.layout.dialog_edit_interval

    companion object {

        fun newInstance(interval: Interval): IntervalEditDialog {
            val dialog = IntervalEditDialog()

            dialog.arguments = Bundle().apply {
                putInt(EXTRA_WORK_TIME, interval.work)
                putInt(EXTRA_REST_TIME, interval.rest)
                putInt(EXTRA_TYPE, interval.type)
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

        repeatsCbGroup.visibility = View.GONE

        dialogTitle.text = resources.getString(R.string.dialog_interval_setting_title)

        cbName.setOnCheckedChangeListener { _, isChecked -> onNameCheckBoxClick(isChecked) }

        saveButton.setOnClickListener { onSaveButtonClick() }
        deleteButton.setOnClickListener { onDeleteButtonClick() }

        setTypeSpinner()
        prepareEditText()
    }

    private fun prepareEditText() {

        arguments?.let {
            if (it.getString(EXTRA_INTERVAL_NAME) != EMPTY) {
                cbName.isChecked = false
                etName.visibility = View.VISIBLE
                etName.setText(it.getString(EXTRA_INTERVAL_NAME))
            }

            typeSpinner.setSelection((it.getInt(EXTRA_TYPE)))
            etWork.setValue(it.getInt(EXTRA_WORK_TIME))
            etRest.setValue(it.getInt(EXTRA_REST_TIME))
        }


    }

    private fun setTypeSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.type_list,
            R.layout.view_spinner_selected
        )
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner_drop)
        typeSpinner.adapter = arrayAdapter

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    Constants.WITH_REST_TYPE -> {
                        restGroup.visibility = View.VISIBLE
                        workGroup.visibility = View.VISIBLE
                    }
                    Constants.WITHOUT_REST_TYPE -> {
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

    private fun onSaveButtonClick() {
        var name = EMPTY
        val type = typeSpinner.selectedItemPosition
        if (checkNotEmpty(etName))
            name = etName.text.toString()

        presenter.saveIntervalButtonClicked(name, type, etWork.getValue(), etRest.getValue())
        dismiss()
    }

    private fun onDeleteButtonClick() {
        dismiss()
        presenter.deleteIntervalButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        etRepeats.isCursorVisible = false
        etName.isCursorVisible = false
    }
}
package com.foxek.simpletimer.presentation.interval.dialog

import android.os.Bundle
import android.view.View

import com.foxek.simpletimer.R
import com.foxek.simpletimer.presentation.base.BaseDialog
import com.foxek.simpletimer.presentation.interval.IntervalContact

import javax.inject.Inject

import com.foxek.simpletimer.common.utils.Constants.EMPTY
import kotlinx.android.synthetic.main.dialog_interval.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.foxek.simpletimer.common.utils.Constants.WITHOUT_REST_TYPE
import com.foxek.simpletimer.common.utils.Constants.WITH_REST_TYPE
class IntervalCreateDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override var dialogTitle = R.string.dialog_interval_create_title

    override fun getLayout() = R.layout.dialog_interval

    companion object {
        fun newInstance(): IntervalCreateDialog = IntervalCreateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_interval_delete_btn.visibility = View.GONE

        dialog_interval_checkbox_repeats.setOnCheckedChangeListener { _, isChecked -> onRepeatsCheckBoxClick(isChecked) }
        dialog_interval_checkbox_name.setOnCheckedChangeListener { _, isChecked -> onNameCheckBoxClick(isChecked) }

        dialog_interval_save_btn.setOnClickListener { onSaveButtonClick() }

        setTypeSpinner()
        prepareEditText()
    }

    private fun prepareEditText() {
        dialog_interval_field_work.setValue(0)
        dialog_interval_field_rest.setValue(0)

        dialog_interval_field_repeats.setText("1")
    }

    private fun setTypeSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(
                context!!,
                R.array.type_list,
                R.layout.view_spinner_selected
        )
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner_drop)
        dialog_interval_spinner_type.adapter = arrayAdapter

        dialog_interval_spinner_type.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    WITH_REST_TYPE -> {
                        dialog_interval_group_rest.visibility = View.VISIBLE
                        dialog_interval_group_work.visibility = View.VISIBLE
                    }
                    WITHOUT_REST_TYPE -> {
                        dialog_interval_group_rest.visibility = View.GONE
                        dialog_interval_group_work.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun onNameCheckBoxClick(isChecked: Boolean) {
        if (isChecked) {
            dialog_interval_field_name.setText(EMPTY)
            dialog_interval_field_name.visibility = View.GONE
        } else {
            dialog_interval_field_name.visibility = View.VISIBLE
        }
    }

    private fun onRepeatsCheckBoxClick(isChecked: Boolean) {
        if (isChecked) {
            dialog_interval_group_repeats.visibility = View.VISIBLE
        } else {
            dialog_interval_group_repeats.visibility = View.GONE
        }
    }

    private fun onSaveButtonClick() {

        val repeat = if (checkNotEmpty(dialog_interval_field_repeats)) dialog_interval_field_repeats.text.toString().toInt() else 1
        var name = EMPTY

        val type = dialog_interval_spinner_type.selectedItemPosition

        if (checkNotEmpty(dialog_interval_field_name))
            name = dialog_interval_field_name.text.toString()

        for (i in 1..repeat)
            presenter.createIntervalButtonClicked(name, type, dialog_interval_field_work.getValue(), dialog_interval_field_rest.getValue())

        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        dialog_interval_field_repeats.isCursorVisible = false
        dialog_interval_field_name.isCursorVisible = false
    }
}
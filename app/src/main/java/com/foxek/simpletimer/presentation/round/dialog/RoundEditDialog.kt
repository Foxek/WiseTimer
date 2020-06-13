package com.foxek.simpletimer.presentation.round.dialog

import android.os.Bundle

import android.view.View
import android.widget.AdapterView
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.BaseDialog
import com.foxek.simpletimer.presentation.round.RoundContact
import com.foxek.simpletimer.common.utils.Constants

import javax.inject.Inject

import com.foxek.simpletimer.common.utils.Constants.EMPTY
import com.foxek.simpletimer.common.utils.Constants.EXTRA_INTERVAL_NAME
import com.foxek.simpletimer.common.utils.Constants.EXTRA_REST_TIME
import com.foxek.simpletimer.common.utils.Constants.EXTRA_TYPE
import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORK_TIME
import kotlinx.android.synthetic.main.dialog_interval.*
import android.widget.ArrayAdapter


class RoundEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: RoundContact.Presenter

    override var dialogTitle = R.string.dialog_interval_setting_title

    override fun getLayout() = R.layout.dialog_interval

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_interval_group_checkbox_repeats.visibility = View.GONE

        dialog_interval_checkbox_name.setOnCheckedChangeListener { _, isChecked -> onNameCheckBoxClick(isChecked) }

        dialog_interval_save_btn.setOnClickListener { onSaveButtonClick() }
        dialog_interval_delete_btn.setOnClickListener { onDeleteButtonClick() }

        setTypeSpinner()
        prepareEditText()
    }

    private fun prepareEditText() {

        arguments?.let {
            if (it.getString(EXTRA_INTERVAL_NAME) != EMPTY) {
                dialog_interval_checkbox_name.isChecked = false
                dialog_interval_field_name.visibility = View.VISIBLE
                dialog_interval_field_name.setText(it.getString(EXTRA_INTERVAL_NAME))
            }

            dialog_interval_spinner_type.setSelection((it.getInt(EXTRA_TYPE)))
            dialog_interval_field_work.setValue(it.getInt(EXTRA_WORK_TIME))
            dialog_interval_field_rest.setValue(it.getInt(EXTRA_REST_TIME))
        }


    }

    private fun setTypeSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(
                context!!,
                R.array.type_list,
                R.layout.view_spinner_selected
        )
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner_drop)
        dialog_interval_spinner_type.adapter = arrayAdapter

        dialog_interval_spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    Constants.WITH_REST_TYPE -> {
                        dialog_interval_group_rest.visibility = View.VISIBLE
                        dialog_interval_group_work.visibility = View.VISIBLE
                    }
                    Constants.WITHOUT_REST_TYPE -> {
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

    private fun onSaveButtonClick() {
        var name = EMPTY
        val type = dialog_interval_spinner_type.selectedItemPosition
        if (checkNotEmpty(dialog_interval_field_name))
            name = dialog_interval_field_name.text.toString()

        presenter.onSaveRoundBtnClick(name, type, dialog_interval_field_work.getValue(), dialog_interval_field_rest.getValue())
        dismiss()
    }

    private fun onDeleteButtonClick() {
        dismiss()
        presenter.onDeleteRoundBtnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dialog_interval_field_repeats.isCursorVisible = false
        dialog_interval_field_name.isCursorVisible = false
    }

    companion object {
        fun newInstance(round: Round): RoundEditDialog {
            val dialog = RoundEditDialog()

            dialog.arguments = Bundle().apply {
                putInt(EXTRA_WORK_TIME, round.workInterval)
                putInt(EXTRA_REST_TIME, round.restInterval)
                putInt(EXTRA_TYPE, round.type)
                putString(EXTRA_INTERVAL_NAME, round.name)
            }
            return dialog
        }
    }
}
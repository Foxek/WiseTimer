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
import kotlinx.android.synthetic.main.dialog_round.*
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.foxek.simpletimer.di.PerFragment

class RoundEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: RoundContact.Presenter

    override var dialogTitle = R.string.dialog_interval_setting_title

    override fun getLayout() = R.layout.dialog_round

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_round_group_checkbox_repeats.isVisible = false

        arguments?.let {
            setRoundType(it.getInt(EXTRA_TYPE))
            prepareTypeSpinner(it.getInt(EXTRA_TYPE))
            prepareEditText(
                it.getString(EXTRA_INTERVAL_NAME).orEmpty(),
                it.getInt(EXTRA_WORK_TIME),
                it.getInt(EXTRA_REST_TIME)
            )
        }
    }

    override fun attachListeners() {
        dialog_round_checkbox_name.setOnCheckedChangeListener { _, isChecked ->
            onNameCheckBoxClick(isChecked)
        }
        dialog_round_save_btn.setOnClickListener { onSaveButtonClick() }
        dialog_round_delete_btn.setOnClickListener { onDeleteButtonClick() }
    }

    private fun prepareEditText(roundName: String, workInterval: Int, restInterval: Int) {
        if (roundName.isNotBlank()) {
            dialog_round_checkbox_name.isChecked = false
            dialog_round_field_name.visibility = View.VISIBLE
            dialog_round_field_name.setText(roundName)
        }

        dialog_round_field_work.setValue(workInterval)
        dialog_round_field_rest.setValue(restInterval)
    }

    private fun prepareTypeSpinner(type: Int) {
        val arrayAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.type_list,
            R.layout.view_spinner_selected
        )
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner_drop)

        dialog_round_spinner_type.adapter = arrayAdapter
        dialog_round_spinner_type.setSelection(type)

        dialog_round_spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setRoundType(position)
            }
        }
    }

    private fun onNameCheckBoxClick(isChecked: Boolean) {
        if (isChecked) {
            dialog_round_field_name.setText(EMPTY)
            dialog_round_field_name.visibility = View.GONE
        } else {
            dialog_round_field_name.visibility = View.VISIBLE
        }
    }

    private fun setRoundType(type: Int) {
        when (type) {
            Constants.WITH_REST_TYPE -> {
                dialog_round_group_rest.isVisible = true
                dialog_round_group_work.isVisible = true
            }
            Constants.WITHOUT_REST_TYPE -> {
                dialog_round_group_work.isVisible = true
                dialog_round_group_rest.isVisible = false
            }
        }
    }

    private fun onSaveButtonClick() {
        var name = EMPTY
        val type = dialog_round_spinner_type.selectedItemPosition
        if (checkNotEmpty(dialog_round_field_name))
            name = dialog_round_field_name.text.toString()

        presenter.onSaveRoundBtnClick(
            name,
            type,
            dialog_round_field_work.getValue(),
            dialog_round_field_rest.getValue()
        )
        dismiss()
    }

    private fun onDeleteButtonClick() {
        dismiss()
        presenter.onDeleteRoundBtnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dialog_round_field_repeats.isCursorVisible = false
        dialog_round_field_name.isCursorVisible = false
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
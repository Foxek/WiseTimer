package com.foxek.simpletimer.ui.interval.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseDialog
import com.foxek.simpletimer.ui.interval.IntervalContact

import javax.inject.Inject
import androidx.constraintlayout.widget.Group

import butterknife.BindView
import butterknife.OnClick

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.EXTRA_INTERVAL_NAME
import com.foxek.simpletimer.utils.Constants.EXTRA_REST_TIME
import com.foxek.simpletimer.utils.Constants.EXTRA_WORK_TIME
import com.foxek.simpletimer.utils.IntervalUtils.convertToSeconds
import com.foxek.simpletimer.utils.IntervalUtils.formatEditTextData
import kotlinx.android.synthetic.main.dialog_edit_interval.*

class IntervalEditDialog : BaseDialog() {

    @Inject
    lateinit var presenter: IntervalContact.Presenter

    override val dialogTag = "IntervalEditDialog"

    companion object {

        fun newInstance(name: String, work_time: Int, rest_time: Int): IntervalEditDialog {
            val mIntervalEditDialog = IntervalEditDialog()
            val args = Bundle()
            args.putInt(EXTRA_WORK_TIME, work_time)
            args.putInt(EXTRA_REST_TIME, rest_time)
            args.putString(EXTRA_INTERVAL_NAME, name)
            mIntervalEditDialog.arguments = args
            return mIntervalEditDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false)

        getActivityComponent()?.inject(this)
        dialog?.setCanceledOnTouchOutside(true)

        return dialogView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareEditText(arguments!!.getString(EXTRA_INTERVAL_NAME)!!,
                arguments!!.getInt(EXTRA_WORK_TIME),
                arguments!!.getInt(EXTRA_REST_TIME))

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

    private fun repairMemoryLeak() {
        etWorkMinutes.isCursorVisible = false
        etWorkSeconds.isCursorVisible = false

        etRestMinutes.isCursorVisible = false
        etRestSeconds.isCursorVisible = false

        etRepeatsCount.isCursorVisible = false
        etIntervalName.isCursorVisible = false
    }

    fun onSaveButtonClick() {
        var workTime: Int
        var restTime: Int
        var name = EMPTY

        if ((etWorkMinutes.text.toString() != EMPTY) and (etWorkSeconds.text.toString() != EMPTY)) {
            workTime = convertToSeconds(etWorkMinutes.text.toString(), etWorkSeconds.text.toString())
            if (workTime == 0) workTime = 1
        } else
            workTime = 1


        if ((etRestMinutes.text.toString() != EMPTY) and (etRestSeconds.text.toString() != EMPTY)) {
            restTime = convertToSeconds(etRestMinutes.text.toString(), etRestSeconds.text.toString())
            if (restTime == 0) restTime = 1
        } else
            restTime = 1

        if (etIntervalName.text.toString() != EMPTY)
            name = etIntervalName.text.toString()

        presenter.saveIntervalButtonClicked(name, workTime, restTime)
        repairMemoryLeak()
        dismiss()
    }

    fun onDeleteButtonClick() {
        repairMemoryLeak()
        dismiss()
        presenter.deleteIntervalButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repairMemoryLeak()
    }
}
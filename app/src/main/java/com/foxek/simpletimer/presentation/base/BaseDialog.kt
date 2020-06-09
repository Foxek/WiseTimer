package com.foxek.simpletimer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.foxek.simpletimer.R
import com.foxek.simpletimer.di.component.ActivityComponent
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import kotlinx.android.synthetic.main.base_dialog.base_dialog_description
import kotlinx.android.synthetic.main.base_dialog.base_dialog_title

abstract class BaseDialog : DialogFragment(), MvpView {

    abstract var dialogTitle: Int
    open var dialogDescription: Int = NO_ID_INT

    val activityComponent: ActivityComponent?
        get() = (targetFragment as BaseFragment).component

    abstract fun getLayout(): Int

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View? {
        dialog?.setCanceledOnTouchOutside(true)
        return inflater.inflate(R.layout.base_dialog, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val placeHolder = view.findViewById(R.id.base_dialog_layout_container) as FrameLayout
        layoutInflater.inflate(getLayout(), placeHolder)

        setupHeader()
    }

    fun checkNotEmpty(vararg widgets: EditText): Boolean {
        return widgets.all { it.text.isNotEmpty() }
    }

    private fun setupHeader() {
        base_dialog_title.text = getString(dialogTitle)

        if (dialogDescription != NO_ID_INT)
            base_dialog_description.text = getString(dialogDescription)
        else
            base_dialog_description.isVisible = false
    }

}